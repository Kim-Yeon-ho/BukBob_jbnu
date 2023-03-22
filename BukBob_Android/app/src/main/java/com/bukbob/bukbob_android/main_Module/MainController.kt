package com.bukbob.bukbob_android.main_Module

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.databinding.ActivityMainBinding
import com.bukbob.bukbob_android.mainList_Module.FoodListDataModel
import com.bukbob.bukbob_android.mainList_Module.FoodListViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainController(
    private val mainViewModel: MainViewModel,
    private val foodViewModel : FoodListViewModel,
    private val owner: MainActivity,
    private val binding: ActivityMainBinding,
    private val context : Context
    ):AppCompatActivity()
{
    var foodArrayBreakFast : ArrayList<FoodListDataModel.FoodList> = ArrayList(8)
    var foodArrayLunch : ArrayList<FoodListDataModel.FoodList> = ArrayList(8)
    var foodArrayDinner : ArrayList<FoodListDataModel.FoodList> = ArrayList(8)
    private val currentTime: String = SimpleDateFormat("E", Locale.KOREA).format(Date())
    private val dbTime: String = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date())
    private val sharedPreferencesName : String = "updateInfo"

     fun setObservers(){
        val mainViewModelObserver = Observer<Boolean> {
            updateFoodList(binding)
        }
        mainViewModel.isButtonCheck.observe(owner, mainViewModelObserver)

        val foodViewModelLunchObserver = Observer<FoodListDataModel.FoodList> {
            if(it != null) {
                if (it.state != "") {
                    foodArrayLunch.add(it)
                }
            }
        }
        foodViewModel.lunchFoodItem.observe(owner, foodViewModelLunchObserver)

        val foodViewModelDinnerObserver = Observer<FoodListDataModel.FoodList> {
            if(it != null) {
                if (it.state != "") {
                    foodArrayDinner.add(it)
                }
            }
        }
        foodViewModel.dinnerFoodItem.observe(owner, foodViewModelDinnerObserver)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFoodList(binding: ActivityMainBinding) {
        binding.mainViewPager.adapter?.notifyDataSetChanged()
    }

    /**
     * 우선, Parser가 진수원,후생관,의대까지 구현되어 있어 아침 식단 정보를 구하는 함수는 구현하지 않아 위와 같이 구성되었습니다.
     * 작업이 끝나면 처음 로딩 화면인 lottie를 Gone 상태로 만들고, 뷰 페이저를 업데이트 합니다.
     * 뷰 페이저는 메인 어뎁터에게 (아침,점심,저녁)식단을 넘겨줍니다.
     * */

    suspend fun setView(){
        foodViewModel.getFoodListLunch(currentTime, "Jinswo", "", foodViewModel)
        foodViewModel.getFoodListLunch(currentTime, "Medical", "", foodViewModel)
        foodViewModel.getFoodListLunch(currentTime, "Husaeng", "", foodViewModel)
        foodViewModel.getFoodListDinner(currentTime, "Jinswo", "night", foodViewModel)
        foodViewModel.getFoodListDinner(currentTime, "Medical", "night", foodViewModel)

        binding.mainViewPager.adapter =
            MainAdapter(owner, foodArrayBreakFast, foodArrayLunch, foodArrayDinner)
        binding.mainViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainIndicator.attachTo(binding.mainViewPager)

        binding.lottie.visibility = View.GONE
        binding.lottie.cancelAnimation()

        if(readDBInfo() == "없음" && foodArrayDinner.size != 0) {
            setUpdataInfo(foodArrayDinner[0].Title)
        }
    }

    fun checkDb(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
            when (readDbCallDate()) {
                "없음" -> {
                    setDbCallDate()
                }
                dbTime -> {
                    if(!(readDBInfo().equals("없음"))) {
                        dbNetWorkDisconnect()
                    }
                }
                else -> {
                    setDbCallDate()
                }
            }
        }
    }
}

    private fun dbNetWorkDisconnect(){
        CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    val db = Firebase.firestore
                    db.disableNetwork()
                }
        }
    }

    private fun readDbCallDate(): String? {
        val shared = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

        return shared.getString("Date", "없음")
    }

    private fun readDBInfo(): String? {
        val shared = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

        return shared.getString("updateInfo", "없음")
    }

    private fun setDbCallDate(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val shared = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
                val editor = shared.edit()
                editor.putString("Date", dbTime)
                editor.apply()
            }
        }
    }

    private fun setUpdataInfo(info:String){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val shared = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
                val editor = shared.edit()
                editor.putString("updateInfo", info)
                editor.apply()
            }
        }
    }

}