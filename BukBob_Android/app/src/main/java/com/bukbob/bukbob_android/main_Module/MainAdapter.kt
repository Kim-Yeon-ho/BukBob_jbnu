/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.main_Module

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.databinding.FoodListViewBinding
import com.bukbob.bukbob_android.mainList_Module.FoodListAdapter
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter(private val owner: MainActivity): RecyclerView.Adapter<MainAdapter.PagerViewHolder>() {
    val pageCounter = 3

    inner class PagerViewHolder(private val binding: FoodListViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val mainViewModel: MainViewModel = ViewModelProvider(owner).get(MainViewModel::class.java)
            val mainViewModelObserver = Observer<Boolean> {
                updateFoodList(binding)
            }
            mainViewModel.isButtonCheck.observe(owner, mainViewModelObserver)

            setTitle(binding, position)
            setTime(binding)
            setList(binding)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder(FoodListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = pageCounter

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(position)
    }



    fun setList(binding: FoodListViewBinding) {
        val layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        binding.FoodListView.layoutManager = layoutManager
        binding.FoodListView.adapter = FoodListAdapter(arrayListOf("1", "2", "3"), owner)
    }
    /**
     * setList()?
     * 음식 리스트 어댑터에 식단 리스트를 넘겨주고 출력하는 함수입니다.
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
        val currentTime = SimpleDateFormat("HH:mm", Locale.KOREA).format(Date())
        binding.FoodTime.text = "현재 시각 : $currentTime"
    }
    /**
     * setTime()?
     * 해당 함수는 실제 시간을 지정하는 함수입니다.
     * */

    fun updateFoodList(binding: FoodListViewBinding) {
        binding.FoodListView.adapter?.notifyDataSetChanged()
    }

    /**
     * updateFoodList()?
     * 해당 함수는
     * */

}