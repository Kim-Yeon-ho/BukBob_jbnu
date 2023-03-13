package com.bukbob.bukbob_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.bukbob.bukbob_android.main_Module.MainAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity() {

    lateinit var mainView : ViewPager2
    lateinit var dotsIndicator: DotsIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainView = findViewById(R.id.mainViewPager)
        mainView.adapter = MainAdapter(arrayListOf("1","2","3","4","5"));
        mainView.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        dotsIndicator = findViewById(R.id.mainIndicator)
        dotsIndicator.attachTo(mainView)

    }
}