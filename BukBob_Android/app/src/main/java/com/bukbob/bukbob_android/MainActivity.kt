/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android

import android.annotation.SuppressLint
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

        setFoodData()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFoodList(binding: ActivityMainBinding) {
        binding.mainViewPager.adapter?.notifyDataSetChanged()
    }

    private fun setFoodData(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                val currentTime = SimpleDateFormat("E", Locale.KOREA).format(Date())

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

}
