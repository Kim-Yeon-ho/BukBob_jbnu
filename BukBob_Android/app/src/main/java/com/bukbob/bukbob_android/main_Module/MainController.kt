/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.main_Module

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.databinding.ActivityMainBinding
import com.bukbob.bukbob_android.mainList_Module.FoodListDataModel
import com.bukbob.bukbob_android.mainList_Module.FoodListViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainController(
    private val owner: MainActivity,
    private val binding: ActivityMainBinding,
    private val context: Context
) : AppCompatActivity() {
    private val currentTime: String = SimpleDateFormat("E", Locale.KOREA).format(Date())
    private val dbTime: String = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date())
    private val sharedPreferencesName: String = "updateInfo"
    private val mainViewModel: MainViewModel = ViewModelProvider(owner)[MainViewModel::class.java]
    private val foodViewModel: FoodListViewModel = ViewModelProvider(owner)[FoodListViewModel::class.java]
    private var foodArrayBreakFast: ArrayList<FoodListDataModel.FoodList> = ArrayList(8)
    private var foodArrayLunch: ArrayList<FoodListDataModel.FoodList> = ArrayList(8)
    private var foodArrayDinner: ArrayList<FoodListDataModel.FoodList> = ArrayList(8)


    /**
     * 각 배열 3개는 각 식당의 아침, 점심, 저녁을 담을 배열입니다.
     * currentTime은 현재 기기의 요일을 가져옵니다. ex.) 월
     * dbTime은 현재 기기의 년월일을 가져옵니다. ex.)20230322
     * sharedPreferencesName은 sharedPreferences를 사용할 때 파일 이름입니다.
     * */

    fun setObservers() {
        val mainViewModelObserver = Observer<Boolean> {
            updateFoodList(binding)
        }
        mainViewModel.isButtonCheck.observe(owner, mainViewModelObserver)

        val foodViewModelLunchObserver = Observer<FoodListDataModel.FoodList> {
            if (it != null) {
                if (it.state != "") {
                    foodArrayLunch.add(it)
                }
            }
        }
        foodViewModel.lunchFoodItem.observe(owner, foodViewModelLunchObserver)

        val foodViewModelDinnerObserver = Observer<FoodListDataModel.FoodList> {
            if (it != null) {
                if (it.state != "") {
                    foodArrayDinner.add(it)
                }
            }
        }
        foodViewModel.dinnerFoodItem.observe(owner, foodViewModelDinnerObserver)
    }

    /**
     * setObservers()?
     * 해당 함수는 각 mainViewModel, foodViewModel의 위젯 버튼 클릭 변수 및 foodList 객체의 업데이트 유/무를 지켜보는 옵저버입니다.
     * if문으로 빈 객체는 제외시키고, state가 없는 더미 데이터를 제외 시켰습니다.
     * */

    @SuppressLint("NotifyDataSetChanged")
    fun updateFoodList(binding: ActivityMainBinding) {
        binding.mainViewPager.adapter?.let{it.notifyDataSetChanged()}
    }

    /**
     * updateFoodList()?
     * 위젯 버튼 클릭 유무에 따른 뷰 페이저 업데이트 함수입니다.
     * */

    suspend fun setView() = coroutineScope {

        launch {
            withContext(Dispatchers.IO) {
                foodViewModel.getFoodListLunch(currentTime, "Jinswo", "", foodViewModel)
                foodViewModel.getFoodListLunch(currentTime, "Medical", "", foodViewModel)
                foodViewModel.getFoodListLunch(currentTime, "Husaeng", "", foodViewModel)
                foodViewModel.getFoodListDinner(currentTime, "Jinswo", "night", foodViewModel)
                foodViewModel.getFoodListDinner(currentTime, "Medical", "night", foodViewModel)
            }

            binding.mainViewPager.adapter =
                MainAdapter(owner, foodArrayBreakFast, foodArrayLunch, foodArrayDinner)
            binding.mainViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.mainIndicator.attachTo(binding.mainViewPager)

            binding.lottie.visibility = View.GONE
            binding.lottie.cancelAnimation()

            if (readDbInfo() == "없음" && foodArrayDinner.size != 0) {
                setUpdateInfo(foodArrayDinner[0].Title)
            }
        }

    }

    /**
     * setView()?
     * setView 함수는 뷰 페이저의 리사이클러뷰에 데이터를 세팅해주는 함수입니다.
     * 메인 뷰 페이저 어뎁터에게 각 음식 정보가 담긴 리스트를 넘겨줍니다.
     * lottie로 구성된 로딩 화면을 제어합니다.
     * */



    fun checkDb() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                when (readDbCallDate()) {
                    "없음" -> {
                        setDbCallDate()
                    }
                    dbTime -> {
                        if (!(readDbInfo().equals("없음"))) {
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

    /**
     * checkDb()?
     * 코루틴을 적용해 사용자가 가지고 있는 파이어베이스 로컬 캐시가 최신 버전인지 확인합니다.
     * 최신 버전을 경우 앱에서 네트워크를 차단하는 함수를 호출합니다.
     * */

    private fun dbNetWorkDisconnect() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val db = Firebase.firestore
                db.disableNetwork()
            }
        }
    }

    /**
     * 파이어베이스의 인터넷을 차단합니다.
     * 파이어베이스 특성상 인터넷이 작동하지 않을경우 로컬 캐시 데이터를 가져옵니다.
     * 이를 활용해 기존 캐시 데이터로 식단을 설정하여 서버의 부하를 낮춥니다.
     * 또한, 앞전의 checkDb() 함수로 인해 최신 데이터의 유무를 확인하기 때문에
     * 사용자는 늘 최신 식단을 보장받습니다.
     * */

    private fun readDbCallDate(): String? {
        val shared = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

        return shared.getString("Date", "없음")
    }

    /**
     * readDbCallDate()?
     * SharedPreferences에서 Date라는 키 값을 가진 데이터의 정보를 읽어옵니다.
     * 해당 변수에는 데이터 베이스 업데이트 날짜가 기록되어 있습니다.
     * */

    private fun readDbInfo(): String? {
        val shared = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

        return shared.getString("updateInfo", "없음")
    }

    /**
     * readDbInfo()?
     * SharedPreferences에서 updateInfo라는 키 값을 가진 데이터의 정보를 읽어옵니다.
     * 해당 변수에는 데이터 베이스 업데이트 당시 받은 데이터가 들어있습니다.
     * 이는 인터넷이 불안정한 상태에서 차후 업데이트를 보장하기 위한 2차 안전장치입니다.
     *
     * 만약, 인터넷이 업데이트 도중 끊긴 다면 아래와 같은 문제가 발생합니다.
     *
     * 파이어베이스 업데이트 -> 인터넷 끊김 -> 데이터 누락 -> 업데이트 날짜 기록됨
     * 차후 실행 -> 업데이트 날짜 확인됨 -> 인터넷 차단 -> 캐시로드 -> 빈 캐시 정보 로드 -> 빈 화면
     *
     * 위와 같은 에러를 막기 위해 "업데이트 날짜 확인됨" 구간 뒤에 -> "업데이트 당시 받은 데이터 기록"을
     * 추가하여 한번 더 확인하는 구성입니다.
     *
     * */

    private fun setDbCallDate() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val shared =
                    context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
                val editor = shared.edit()
                editor.putString("Date", dbTime)
                editor.apply()
            }
        }
    }

    /**
     * setDbCallDate()?
     * 파이어베이스 업데이트가 끝나면 업데이트 날짜를 SharedPreferences에 기록해줍니다.
     * */

    private fun setUpdateInfo(info: String) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val shared =
                    context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
                val editor = shared.edit()
                editor.putString("updateInfo", info)
                editor.apply()
            }
        }
    }

    /**
     * setUpdataInfo()?
     * 파이어베이스 업데이트가 끝나면 2차 확인을 위해 업데이트 정보를 기록합니다.
     * */


}