/**
 * 2023 / 03 / 15 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.mainList_Module

import com.bukbob.bukbob_android.R
import com.bukbob.bukbob_android.main_Module.MainViewModel


// 해당 함수는 FoodListAdapter에 의해 호출됩니다.
class FoodListController(val listViewModel: MainViewModel, val holder: FoodListAdapter.ViewHolder) {

    fun checkStarButton(position: Int){
        val isDifferentStart = isDifferentStartCheck(position)
        listViewModel.setPosition(position)

        if(isDifferentStart){
            setStartButtonState()
        }else{
            listViewModel.setIsCheck(true)
            holder.widget.setImageResource(R.drawable.starfill)
        }
    }

    /**
     * checkStarButton 함수()?
     *
     * 해당 함수는 리사이클러뷰 안에 위젯 버튼이 서로 다른 위치에서 눌린건지, 같은 위치에서 눌린건지 판단하는 함수입니다.
     *
     * 첫번째로 똑같은 위치의 위젯 즐겨찾기 버튼을 눌렀을 때 값을 비교하고 적절하게 버튼의 상태 변경을 호출합니다.
     * 두번째로 다른 위치의 위젯 즐겨찾기 버튼을 눌렀을 때 라이브 데이터를 변경하고 클릭한 버튼을 즐겨찾기 처리합니다.
     * */

    fun setStartButtonState(){
        if(listViewModel.isButtonCheck.value == false){
            listViewModel.setIsCheck(true)
            holder.widget.setImageResource(R.drawable.starfill)
        }else{
            listViewModel.setIsCheck(false)
            holder.widget.setImageResource(R.drawable.starnonfill)
        }
    }

    /**
     * setStartButtonState()?
     *
     * 해당 함수는 checkStarButton()함수에서 같은 위치의 버튼을 클릭한 것인지,
     * 다른 위치의 버튼을 클릭한 것인지 분기가 정해지고 같은 위치의 버튼을 클릭 했을때 처리해주는 함수입니다.
     * */


    fun asyncStartButton(position: Int){
        if(listViewModel.position.value == position && listViewModel.isButtonCheck.value == true){
            holder.widget.setImageResource(R.drawable.starfill)
        }else{
            holder.widget.setImageResource(R.drawable.starnonfill)
        }
    }

    /**
     * asyncStartButton()?
     *
     * 해당 함수는 각 페이지마다 위젯 즐겨찾기 버튼의 클릭 유/무를 동기화해줍니다.
     * */

    fun isDifferentStartCheck(position: Int) : Boolean{
        return listViewModel.position.value == position
    }

    /**
     * isDifferentStartCheck()?
     *
     * 해당 함수는 서로 다른 위치의 위젯 즐겨찾기 버튼을 눌렀는지 비교하는 함수입니다.
     * */

}