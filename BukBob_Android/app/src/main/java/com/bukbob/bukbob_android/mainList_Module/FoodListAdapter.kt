/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.mainList_Module

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.databinding.FoodListItemViewBinding

/**
 * 해당 어댑터는 음식 식단 리스트를 출력해주는 어댑터입니다.
 * MainAdapter의 호출에 의해 실행됩니다.
 * */

class FoodListAdapter (private var foodList: ArrayList<FoodListDataModel.FoodList>, private val owner : MainActivity): RecyclerView.Adapter<FoodListAdapter.ViewHolder>(){

    private val CLICK_DELAY : Long = 250
    // 즐겨찾기 더블클릭 기능중 딜레이 시간을 관리하는 변수입니다.

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

    private var doubleClickCounter = 0
    private lateinit var foodController : FoodListViewController

    /**
     * doubleClickCounter는 더블 클릭 이벤트 구별에 사용되는 카운터 변수입니다.
     * foodController는 FoodList를 관리하는 함수가 모여있는 객체입니다.
     * */

    inner class ViewHolder(val binding: FoodListItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FoodListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    /**
     * 최초에 ViewHolder가 생성되면 ViewHolder를 뷰홀더에 리턴해줍니다.
     * */

    override fun getItemCount(): Int = foodList.size
    //FoodList.size
    //FoodList 사이즈만큼 리스트를 생성합니다.

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        foodController = FoodListViewController(owner, holder)
        //FoodListController의 객체를 선언합니다.

        val title = foodList[position].Title
        val state = foodList[position].state
        val isSave = foodController.checkPref(title)

        foodController.setFoodList(title, foodList[position].List)

        holder.binding.widgetButton.setOnClickListener {
            foodController.setButton(position,title,isSave,state)
        }

        /**
         * 위젯버튼 클릭 이벤트를 처리합니다.
         * */

        holder.binding.foodItemBox.setOnClickListener {
            doubleClickCounter++
            when (doubleClickCounter) {
                1 -> handler.postDelayed(clickRunnable, CLICK_DELAY)
                2 -> {
                    foodController.checkStarButton(position, title,state)
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