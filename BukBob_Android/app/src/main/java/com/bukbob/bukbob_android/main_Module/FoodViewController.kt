/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.main_Module

import androidx.recyclerview.widget.LinearLayoutManager
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.mainList_Module.FoodListAdapter
import java.text.SimpleDateFormat

class FoodViewController(hold : MainAdapter.PagerViewHolder, pageIndex: Int, owner: MainActivity) {

    val owner = owner
    val holder : MainAdapter.PagerViewHolder
    val position : Int
    // Holder를 담기 위한 상수입니다.
    // postion은 ViewHolder가 생성 되고 나서 현재 사용자가 보고 있는 페이지를 담고 있는 상수입니다.

    init {
        holder = hold
        position = pageIndex
    }

    /**
     * 최초 객체가 생성될 때 hold라는 매개변수에 MainAdapter에서 holder를 인자값으로 받아
     * 해당 class에서 사용합니다.
     * */

    fun setList(holder: MainAdapter.PagerViewHolder){
        val layoutManager = LinearLayoutManager(holder.itemView.context,
            LinearLayoutManager.VERTICAL,false)

        holder.foodList.layoutManager = layoutManager
        holder.foodList.adapter = FoodListAdapter(arrayListOf("1","2","3"),owner)
        //해당 부분에서 파이어베이스 정보를 전달해야합니다.
    }

    /**
     * 해당 함수는 FoodListView 리사이클러뷰에 식단을 전달하는 함수입니다.
     * 차후 Firebase 모듈과 결합하여 사용하면 좋을것 같습니다.
     * */

    fun setTitle(holder : MainAdapter.PagerViewHolder, position: Int){
        when(position){
            0 -> {
                holder.foodTitle.text = "조식"
                holder.foodTime.text = "07:00 ~ 08:30"
            }
            1 -> {
                holder.foodTitle.text = "중식"
                holder.foodTime.text = "11:00 ~ 14:00"
            }
            2 -> {
                holder.foodTitle.text = "석식"
                holder.foodTime.text = "17:30 ~ 19:00"
            }
        }
    }

    /**
     * 해당 함수는 사용자가 보고 있는 페이지
     * 0 -> 조식 , 1 -> 중식 , 3 -> 석식
     * 페이지를 when을 사용하여 각 분기별로 처리한 함수입니다.
     * */

    fun setTime(holder: MainAdapter.PagerViewHolder){
        val currentTime : Long = System.currentTimeMillis()
        val nowTime = SimpleDateFormat("MM/dd E")
        holder.foodDate.text = nowTime.format(currentTime)
    }

    /**
     * 해당 함수는 현재 Android 핸드폰의 시간을 밀리세컨드 단위로 가져옵니다.
     * 그후 지정된 포맷에 맞게 시간을 변형하여 사용자에게 제공합니다.
     *
     * 시스템 언어에 따라 요일이 한글 또는 영어 or 다른 언어로 출력될 수 있습니다.
     * */

    fun updateFoodList(){
        holder.foodList.adapter?.notifyDataSetChanged()
    }

}