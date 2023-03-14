package com.bukbob.bukbob_android.main_Module

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.R
class MainAdapter(var foodList: ArrayList<String>,owner : MainActivity): RecyclerView.Adapter<MainAdapter.PagerViewHolder>() {

    val owner = owner
    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.food_list_view,parent,false)){

        val foodTitle : TextView = itemView.findViewById(R.id.FoodTitle)
        val foodDate : TextView = itemView.findViewById(R.id.FoodDate)
        val foodTime : TextView = itemView.findViewById(R.id.FoodTime)
        val foodList : RecyclerView = itemView.findViewById(R.id.FoodListView)


        /**
         * 해당 어댑터는 food_list_view.xml 파일을 기준으로 작동합니다.
         *
         * foodTitle : food_list_view.xml안에 조식,중식,석식 타이틀을 담당하는 텍스트 뷰입니다.
         * foodDate : food_list_view.xml안에 월/일을 담당하는 텍스트 뷰입니다.
         * foodTime : food_list_view.xml안에 식당의 운영 시간을 담당하는 텍스트 뷰입니다.
         * foodList : food_list_view.xml안에 음식 리스트를 출력해주는 리사이클러뷰입니다.
         *
         *
         * 차후 ViewBinding을 해야합니다. findViewByID가 성능적 이슈를 불러올 수 있습니다.
         * */

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder(parent)
    // onCreateViewHolder 메소드는 ViewHolder를 생성하는 메소드입니다. 이로 인해 차후에 View를 컨트롤 할 수 있습니다.

    override fun getItemCount(): Int = 3
    //조식,중식,석식 페이지 3개

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {

        val FoodView = FoodViewControll(holder,position,owner)
        // FoodView를 컨트롤하는 객체를 생성합니다.

        val model : MainViewModel = ViewModelProvider(owner).get(MainViewModel::class.java)
        val modeObser = Observer<Boolean> {
            FoodView.updateFoodList()
        }

        model.isCheck.observe(owner,modeObser)

        FoodView.setTitle(holder,position)
        FoodView.setTime(holder)
        FoodView.setList(holder)

    }

    /**
     * 해당 FoodView객체 안에는 각 setTitle(), setTime(), setList() 함수가 내장되어 있습니다.
     * 각 함수는 PagerView inner class에서 언급한 뷰들을 컨트롤합니다.
     *
     *
     * 해당 onBindViewHolder()는 최초에만 실행되어 무분별한 객체 생성이 발생하지 않습니다.
     * 만약, 성능적 이슈가 있다면 Android profiler를 사용해서 분석해주세요.
     * */

}