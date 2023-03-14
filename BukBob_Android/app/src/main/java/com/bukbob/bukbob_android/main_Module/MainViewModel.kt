package com.bukbob.bukbob_android.main_Module

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var isCheck : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var position : MutableLiveData<Int> = MutableLiveData<Int>(-1)

    fun setIsCheck(isTrue : Boolean){
        isCheck.value = isTrue
    }

    fun setPosition(index : Int){
        position.value = index
    }

}