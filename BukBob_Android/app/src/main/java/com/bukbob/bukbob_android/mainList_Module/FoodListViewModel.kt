package com.bukbob.bukbob_android.mainList_Module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodListViewModel : ViewModel() {

    private val foodDbModel = FoodListDataModel()

    var lunchFoodItem : MutableLiveData<FoodListDataModel.FoodList>
    = MutableLiveData(FoodListDataModel.FoodList(arrayListOf(""),"","",""))

    var dinnerFoodItem : MutableLiveData<FoodListDataModel.FoodList>
    = MutableLiveData(FoodListDataModel.FoodList(arrayListOf(""),"","",""))

    suspend fun getFoodListLunch(date:String, marketTitle:String, state:String, foodViewModel : FoodListViewModel){
        foodDbModel.requestFoodListLunch(date,marketTitle, state,foodViewModel)
    }

    suspend fun getFoodListDinner(date:String, marketTitle:String, state:String, foodViewModel : FoodListViewModel){
        foodDbModel.requestFoodListDinner(date,marketTitle, state,foodViewModel)
    }

}
