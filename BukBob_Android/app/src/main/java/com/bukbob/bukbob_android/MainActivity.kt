/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bukbob.bukbob_android.databinding.ActivityMainBinding
import com.bukbob.bukbob_android.mainList_Module.FoodListAdapter
import com.bukbob.bukbob_android.main_Module.MainAdapter
import com.bukbob.bukbob_android.main_Module.MainViewModel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity() {


    lateinit var mbinding: ActivityMainBinding
    val binding get() = mbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainViewPager.adapter = MainAdapter(this)
        //페이지 어댑터를 설정합니다.
        binding.mainViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // 가로 방향 스와이프 지원을 위한 선언입니다.
        binding.mainIndicator.attachTo(binding.mainViewPager)
        // 인디케이터를 mainView에 지정해줍니다.

    }
}