/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.mainList_Module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodListViewModel : ViewModel() {

    private val foodDbModel = FoodListDataModel()
    //FoodListDataModel() 객체를 생성하여 ViewModel의 옵저버 변수를 제어합니다.

    var breakFoodItem : MutableLiveData<FoodListDataModel.FoodList>
            = MutableLiveData(FoodListDataModel.FoodList(arrayListOf(""),"","",""))

    var lunchFoodItem : MutableLiveData<FoodListDataModel.FoodList>
    = MutableLiveData(FoodListDataModel.FoodList(arrayListOf(""),"","",""))

    var dinnerFoodItem : MutableLiveData<FoodListDataModel.FoodList>
    = MutableLiveData(FoodListDataModel.FoodList(arrayListOf(""),"","",""))

    suspend fun getFoodList(date:String, marketTitle:String, state:String, foodViewModel : FoodListViewModel){
        foodDbModel.requestFoodList(date,marketTitle, state,foodViewModel)
    }


}
