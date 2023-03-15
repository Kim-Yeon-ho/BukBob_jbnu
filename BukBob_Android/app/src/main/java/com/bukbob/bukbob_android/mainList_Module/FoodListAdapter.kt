/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.mainList_Module

import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.R
import com.bukbob.bukbob_android.databinding.FoodListItemViewBinding
import com.bukbob.bukbob_android.main_Module.MainViewModel
import java.util.logging.Handler

/**
 * 해당 어댑터는 음식 식단 리스트를 출력해주는 어댑터입니다.
 * MainAdapter의 호출에 의해 실행됩니다.
 * */

class FoodListAdapter (val FoodList: ArrayList<String>,owner : MainActivity): RecyclerView.Adapter<FoodListAdapter.ViewHolder>(){

    private val CLICK_DELAY : Long = 250
    // 즐겨찾기 더블클릭 기능중 딜레이 시간을 관리하는 변수입니다.

    private val listViewModel : MainViewModel = ViewModelProvider(owner).get(MainViewModel::class.java)
    //FoodList 위젯 클릭에 따른 true/false 값을 각 뷰 페이저마다 공유 하기위해 선언된 라이브데이터 객체 입니다.

    private val handler = android.os.Handler(Looper.getMainLooper())
    private val clickRunnable = Runnable {
        run {
            doubleClickCounter = 0
        }
    }

    /**
     * handler는 더블클릭 이벤트를 처리하기위한 타이머입니다.
     * clickRunnable 변수는 250ms 이후에 클릭 카운터를 0으로 초기화합니다.
     *
     * 로직 -> 첫번째 클륵 -> 다음 클릭 대기 -> 두번째 클릭 -> 더블클릭으로 간주.
     *                      or
     * 로직 -> 첫번째 클륵 -> 250ms 초과 -> 0으로 초기화 -> 일반 클릭으로 간주.
     * */

    var doubleClickCounter = 0
    lateinit var foodController : FoodListController

    /**
     * doubleClickCounter는 더블 클릭 이벤트 구별에 사용되는 카운터 변수입니다.
     * foodController는 FoodList를 관리하는 함수가 모여있는 객체입니다.
     * */


    inner class ViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var foodMarket : TextView
        var foodData : TextView
        var widget : ImageButton
        var itemBox : LinearLayout

        init {
            foodMarket = parent.findViewById(R.id.food_market)
            foodData = parent.findViewById(R.id.FoodList)
            widget = parent.findViewById(R.id.widgetButton)
            itemBox = parent.findViewById(R.id.foodItemBox)
        }

    }

    /**
     * 해당 어댑터는 food_list_item_view를 기반으로 작성됐습니다.
     *
     * foodMarket는 food_list_item_view안에 식당 타이틀을 관리합니다.
     * foodData는 food_list_item_view안에 식단 리스트를 관리합니다.
     * widget는 food_list_item_view안에 widget 리스트를 관리합니다.
     * itemBox는 food_list_item_view안에 식당 더블클릭 이벤트를 감지하기 위한 레이아웃 입니다.
     *
     * */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.food_list_item_view, parent, false)


        return ViewHolder(view)
    }

    /**
     * 최초에 ViewHolder가 생성되면 ViewHolder를 뷰홀더에 리턴해줍니다.
     * */

    override fun getItemCount(): Int = FoodList.size
    //FoodList 사이즈만큼 리스트를 생성합니다.

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodMarket.text = "진수당"
        holder.foodData.text = "맛있는 밥 \n커리 돈까스 \n겉절이 김치 \n요플레"

        /**
         * 식당의 이름과 메뉴를 설정하는 부분입니다. 차후 FoodListController에 함수로 구현하면 좋을것 같습니다.
         * */

        foodController = FoodListController(listViewModel, holder)
        //FoodListController의 객체를 선언합니다.
        foodController.asyncStartButton(position)
        //각 페이지마다 즐겨찾기 버튼을 동기화 해주는 함수입니다.

        holder.widget.setOnClickListener {
            foodController.checkStarButton(position)
        }

        /**
         * 위젯버튼 클릭 이벤트를 처리합니다.
         * */

        holder.itemBox.setOnClickListener{
            doubleClickCounter++

            when(doubleClickCounter){
                1 -> handler.postDelayed(clickRunnable,CLICK_DELAY)
                2 -> {
                    foodController.checkStarButton(position)
                    doubleClickCounter = 0
                }
            }
        }

        /**
         * 더블클릭 이벤트를 처리하는 부분입니다.
         * 처음 클릭시 Handler 작동 -> 250ms이후 -> 0으로 초기화 or 더블클릭 이벤트 처리
         * */

    }

}