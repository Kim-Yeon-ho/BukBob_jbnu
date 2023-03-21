package com.bukbob.bukbob_android.mainList_Module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodListViewModel : ViewModel() {

    private val listModel = FoodListDataModel()
    var foodItem : MutableLiveData<FoodListDataModel.FoodList> = MutableLiveData(FoodListDataModel.FoodList(
        arrayListOf(""),"","",""))

    suspend fun getFoodList(date:String, marketTitle:String, state:String, foodViewModel : FoodListViewModel){
        listModel.requestFoodList(date,marketTitle, state,foodViewModel)
    }

}
