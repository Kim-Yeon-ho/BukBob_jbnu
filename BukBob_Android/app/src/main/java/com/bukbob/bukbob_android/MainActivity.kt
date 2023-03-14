package com.bukbob.bukbob_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bukbob.bukbob_android.mainList_Module.FoodListAdapter
import com.bukbob.bukbob_android.main_Module.MainAdapter
import com.bukbob.bukbob_android.main_Module.MainViewModel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity() {

    lateinit var mainView : ViewPager2
    lateinit var mainDotsIndicator: DotsIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainView = findViewById(R.id.mainViewPager)
        mainDotsIndicator = findViewById(R.id.mainIndicator)

        mainView.adapter = MainAdapter(arrayListOf("1","2","3","4","5"),this)
        // 차후 이곳에 Firebase 에서 받아온 정보를 넘겨줘야합니다.
        mainView.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // 가로 방향 스와이프 지원을 위한 선언입니다.
        mainDotsIndicator.attachTo(mainView)
        // 인디케이터를 mainView에 지정해줍니다.

    }
}