package com.bukbob.bukbob_android.mainList_Module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodListViewModel : ViewModel() {

    private val listModel = FoodListDataModel()
    var foodItemLunch : MutableLiveData<FoodListDataModel.FoodList> = MutableLiveData(FoodListDataModel.FoodList(
        arrayListOf(""),"","",""))

    var foodItemDinner : MutableLiveData<FoodListDataModel.FoodList> = MutableLiveData(FoodListDataModel.FoodList(
        arrayListOf(""),"","",""))

    suspend fun getFoodListLunch(date:String, marketTitle:String, state:String, foodViewModel : FoodListViewModel){
        listModel.requestFoodListLunch(date,marketTitle, state,foodViewModel)
    }

    suspend fun getFoodListDinner(date:String, marketTitle:String, state:String, foodViewModel : FoodListViewModel){
        listModel.requestFoodListDinner(date,marketTitle, state,foodViewModel)
    }

}
