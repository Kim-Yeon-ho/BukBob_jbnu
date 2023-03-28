/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.bukbob.bukbob_android.databinding.ActivityMainBinding
import com.bukbob.bukbob_android.main_Module.MainController
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * binding은 ViewBinding을 위해 선언한 변수입니다.
     * */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            val mainController = MainController(this, binding, applicationContext)

            mainController.setObservers()

            CoroutineScope(Dispatchers.Main).launch {
                val callDB = async {
                    mainController.checkDb()
                }
                callDB.await().let {
                    mainController.setView()
                }
            }
    }

    /**
     * 해당 생명주기에서 Observers를 먼저 선언하지 않으면 View가 그려지는 안정성이 떨어집니다.
     * checkDB()에서 데이터베이스 캐시 사용 유/무를 결정하고
     * 이후 View()를 그려주는 함수를 호출합니다.
     * */

}

