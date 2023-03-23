/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val mainController = MainController(this,binding,applicationContext)

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                mainController.checkDb()
            }
            mainController.setObservers()
            mainController.setView()
        }

    }


}

