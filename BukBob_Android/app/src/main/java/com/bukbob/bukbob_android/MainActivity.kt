/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bukbob.bukbob_android.databinding.ActivityMainBinding
import com.bukbob.bukbob_android.mainList_Module.FoodListDataModel
import com.bukbob.bukbob_android.mainList_Module.FoodListViewModel
import com.bukbob.bukbob_android.main_Module.MainAdapter
import com.bukbob.bukbob_android.main_Module.MainViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var foodViewModel: FoodListViewModel
    private var foodArrayBreakFast : ArrayList<FoodListDataModel.FoodList> = ArrayList(8)
    private var foodArrayLunch : ArrayList<FoodListDataModel.FoodList> = ArrayList(8)
    private var foodArrayDinner : ArrayList<FoodListDataModel.FoodList> = ArrayList(8)
    val currentTime = SimpleDateFormat("E", Locale.KOREA).format(Date())
    val dbTime = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date())


    /**
     * binding은 ViewBinding을 위해 선언한 변수입니다.
     * mainViewModel, foodViewModel은 각 뷰 페이저 업데이트를 위한 옵저버 선언을 위한 변수와, 리스트 세팅을 위한 변수입니다.
     * 각 ArrayList는 아침,점심,저녁을 저장하는 변수이고, 사이즈는 차후 추가될 식당 및 테스트를 위해 8로 설정했습니다.
     * */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        foodViewModel = ViewModelProvider(this)[FoodListViewModel::class.java]

        val mainViewModelObserver = Observer<Boolean> {
            updateFoodList(binding)
        }
        mainViewModel.isButtonCheck.observe(this, mainViewModelObserver)

        val foodViewModelLunchObserver = Observer<FoodListDataModel.FoodList> {
            if(it != null) {
                if (it.state != ""&& foodArrayLunch.indexOf(it) == -1) {
                    foodArrayLunch.add(it)
                }
            }
        }
        foodViewModel.foodItemLunch.observe(this, foodViewModelLunchObserver)

        val foodViewModelDinnerObserver = Observer<FoodListDataModel.FoodList> {
            if(it != null) {
                if (it.state != "" && foodArrayDinner.indexOf(it) == -1) {
                    foodArrayDinner.add(it)
                }
            }
        }
        foodViewModel.foodItemDinner.observe(this, foodViewModelDinnerObserver)

        CoroutineScope(Dispatchers.Main).launch {
            checkDb()
        }

        /**
         * ViewModelProvider를 사용해 각 mainViewModel과 foodViewModel의 Provider를 설정합니다.
         * 각 위젯 버튼 클릭 유/무 확인 변수와 점심,저녁 식단 리스트의 정보를 담은 FoodList 데이터 클래스 업데이트를 확인합니다.
         * setFoodData() 함수는 코루틴을 적용하여 각 식단을 호출하는 함수입니다.
         *
         * */

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFoodList(binding: ActivityMainBinding) {
        binding.mainViewPager.adapter?.notifyDataSetChanged()
    }

    private fun setFoodData(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){

                foodViewModel.getFoodListLunch(currentTime,"Jinswo","",foodViewModel)
                foodViewModel.getFoodListLunch(currentTime,"Medical","",foodViewModel)
                foodViewModel.getFoodListLunch(currentTime,"Husaeng","",foodViewModel)
                foodViewModel.getFoodListDinner(currentTime,"Jinswo","night",foodViewModel)
                foodViewModel.getFoodListDinner(currentTime,"Medical","night",foodViewModel)

                binding.lottie.visibility = View.GONE
                binding.lottie.cancelAnimation()
            }

            binding.mainViewPager.adapter = MainAdapter(this@MainActivity,foodArrayBreakFast,foodArrayLunch,foodArrayDinner)
            binding.mainViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.mainIndicator.attachTo(binding.mainViewPager)
        }
    }
    /**
     * 우선, Parser가 진수원,후생관,의대까지 구현되어 있어 아침 식단 정보를 구하는 함수는 구현하지 않아 위와 같이 구성되었습니다.
     * 작업이 끝나면 처음 로딩 화면인 lottie를 Gone 상태로 만들고, 뷰 페이저를 업데이트 합니다.
     * 뷰 페이저는 메인 어뎁터에게 (아침,점심,저녁)식단을 넘겨줍니다.
     * */

    private fun networkDisconnectDb(){
        CoroutineScope(Dispatchers.Main).launch {
            val db = Firebase.firestore

            db.disableNetwork().addOnCompleteListener {
                setFoodData()
            }
        }
    }

    private fun checkDb(){
        CoroutineScope(Dispatchers.Main).launch {
            if (readUpdataDate() == "없음") {
                setUpdataDate()
                setFoodData()
            } else if (readUpdataDate() == dbTime) {
                networkDisconnectDb()
            } else {
                setUpdataDate()
                setFoodData()
            }
        }
    }
    private fun setUpdataDate(){
        CoroutineScope(Dispatchers.Main).launch {
            val shared = getSharedPreferences("updateDay", Context.MODE_PRIVATE)
            val editor = shared.edit()
            editor.putString("Date", dbTime)
            editor.apply()
        }
    }

    private fun readUpdataDate() : String{
        val shared = getSharedPreferences("updateDay",Context.MODE_PRIVATE)
        val str = shared.getString("Date","없음")

        return str.toString()
    }

}
