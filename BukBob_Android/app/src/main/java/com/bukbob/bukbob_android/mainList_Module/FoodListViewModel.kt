/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.mainList_Module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodListViewModel : ViewModel() {

    private val foodDbModel = FoodListDataModel()
    //FoodListDataModel() 객체를 생성하여 ViewModel의 옵저버 변수를 제어합니다.

    var lunchFoodItem : MutableLiveData<FoodListDataModel.FoodList>
    = MutableLiveData(FoodListDataModel.FoodList(arrayListOf(""),"","",""))

    var dinnerFoodItem : MutableLiveData<FoodListDataModel.FoodList>
    = MutableLiveData(FoodListDataModel.FoodList(arrayListOf(""),"","",""))

    suspend fun getFoodListLunch(date:String, marketTitle:String, state:String, foodViewModel : FoodListViewModel){
        foodDbModel.requestFoodListLunch(date,marketTitle, state,foodViewModel)
    }

    /**
     * getFoodListLunch()?
     * 각 식당의 점심 식단을 받아옵니다.
     * */

    suspend fun getFoodListDinner(date:String, marketTitle:String, state:String, foodViewModel : FoodListViewModel){
        foodDbModel.requestFoodListDinner(date,marketTitle, state,foodViewModel)
    }

    /**
     * getFoodListDinner()?
     * 각 식당의 저녁 식단을 받아옵니다.
     * */

}
