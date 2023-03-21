/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.main_Module

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.databinding.FoodListViewBinding
import com.bukbob.bukbob_android.mainList_Module.FoodListAdapter
import com.bukbob.bukbob_android.mainList_Module.FoodListDataModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(private val owner: MainActivity,private var foodArrayBreakFast: ArrayList<FoodListDataModel.FoodList>,private var foodArrayLunch: ArrayList<FoodListDataModel.FoodList>,private var foodArrayDinner: ArrayList<FoodListDataModel.FoodList>): RecyclerView.Adapter<MainAdapter.PagerViewHolder>() {
    private val pageCounter = 3

    inner class PagerViewHolder(private val binding: FoodListViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            setTitle(binding, position)
            setTime(binding)
            setList(binding,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder(FoodListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = pageCounter

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(position)
    }



    fun setList(binding: FoodListViewBinding,position: Int) {
        val layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        binding.FoodListView.layoutManager = layoutManager
        when(position){
            0 -> {
                    binding.FoodListView.adapter = FoodListAdapter(foodArrayBreakFast, owner)
            }
            1 -> {
                    binding.FoodListView.adapter = FoodListAdapter(foodArrayLunch, owner)
            }
            2 -> {
                    binding.FoodListView.adapter = FoodListAdapter(foodArrayDinner, owner)
            }
        }
        //이곳에서 데이터를 넘겨줄것 postion이 아침일 경우 기숙사 식당 밥을 , 점심일 경우 각 식당밥을 , 저녁일 경우도 고려하여 넘겨줄 것 when을 사용해볼 것
    }
    /**
     * setList()?
     * 음식 리스트 어댑터에 식단 리스트를 넘겨주고 출력하는 함수입니다.
     * 차후 sqlite와 같은 라이브러리에서 postion을 사용해 아침,점심,저녁을 구분하고 처리해야합니다.
     * */

    fun setTitle(binding: FoodListViewBinding, position: Int) {
        when (position) {
            0 -> {
                binding.FoodTitle.text = "조식"
                binding.FoodTime.text = "07:00 ~ 08:30"
            }
            1 -> {
                binding.FoodTitle.text = "중식"
                binding.FoodTime.text = "11:00 ~ 14:00"
            }
            2 -> {
                binding.FoodTitle.text = "석식"
                binding.FoodTime.text = "17:30 ~ 19:00"
            }
        }
    }
    /**
     * setTitle()?
     * 해당 함수는 식단 리스트의 각 페이지의 조식,중식,석식 및 시간을 지정하는 함수입니다.
     * */

    fun setTime(binding: FoodListViewBinding) {
        val currentTime = SimpleDateFormat("MM/dd E", Locale.KOREA).format(Date())
        binding.FoodDate.text = currentTime
    }
    /**
     * setTime()?
     * 해당 함수는 실제 날짜를 알려주는 함수입니다.
     * */
}