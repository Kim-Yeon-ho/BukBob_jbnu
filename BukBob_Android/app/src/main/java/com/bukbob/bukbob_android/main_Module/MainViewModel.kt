/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.main_Module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var isButtonCheck : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var position : MutableLiveData<Int> = MutableLiveData<Int>(-1)

    /**
     * 라이브 데이터를 관리하는 부분입니다.
     *
     * isButtonCheck는 위젯 즐겨찾기 버튼의 클릭 유/무를 저장합니다.
     * position은 차후 리사이클러뷰가 재생성 될 때
     * 위젯 즐겨찾기 유/무 버튼을 동기화 하기 위해 사용됩니다.
     *
     * */


    fun setIsCheck(isTrue : Boolean){ isButtonCheck.value = isTrue }

    //isButtonCheck의 값을 변경합니다.

    fun setPosition(index : Int){
        position.value = index
    }
    //postion의 값을 변경합니다.

}