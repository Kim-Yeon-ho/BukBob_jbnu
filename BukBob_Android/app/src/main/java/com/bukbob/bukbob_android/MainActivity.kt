/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bukbob.bukbob_android.databinding.ActivityMainBinding
import com.bukbob.bukbob_android.mainList_Module.FoodListViewModel
import com.bukbob.bukbob_android.main_Module.MainController
import com.bukbob.bukbob_android.main_Module.MainViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var foodViewModel: FoodListViewModel

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

        val mainController = MainController(mainViewModel,foodViewModel,this,binding,applicationContext)

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                mainController.checkDb()
            }
            mainController.setObservers()
            mainController.setView()
        }

    }


}

