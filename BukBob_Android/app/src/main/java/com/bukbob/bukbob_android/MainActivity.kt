/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bukbob.bukbob_android.databinding.ActivityMainBinding
import com.bukbob.bukbob_android.mainList_Module.FoodListDataModel
import com.bukbob.bukbob_android.mainList_Module.FoodListViewModel
import com.bukbob.bukbob_android.main_Module.MainAdapter
import com.bukbob.bukbob_android.main_Module.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var foodViewModel: FoodListViewModel
    var foodArray : ArrayList<FoodListDataModel.FoodList> = ArrayList(6)
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

        val foodViewModelObserver = Observer<FoodListDataModel.FoodList> {
            if(it != null) {
                if (it.state != "") {
                    foodArray.add(it)
                }
            }
        }
        foodViewModel.foodItem?.observe(this, foodViewModelObserver)

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                val currentTime = SimpleDateFormat("E", Locale.KOREA).format(Date())

                foodViewModel.getFoodList(currentTime,"Jinswo","",foodViewModel)
                foodViewModel.getFoodList(currentTime,"Medical","",foodViewModel)
                foodViewModel.getFoodList(currentTime,"Husaeng","",foodViewModel)
                foodViewModel.getFoodList(currentTime,"Jinswo-night","",foodViewModel)
                foodViewModel.getFoodList(currentTime,"Medical-night","",foodViewModel)

            }

            Log.d("데이터",foodArray.size.toString())

            binding.mainViewPager.adapter = MainAdapter(this@MainActivity,foodArray)
            binding.mainViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.mainIndicator.attachTo(binding.mainViewPager)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFoodList(binding: ActivityMainBinding) {
        binding.mainViewPager.adapter?.notifyDataSetChanged()
    }


}
