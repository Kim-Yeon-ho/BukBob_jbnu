package com.bukbob.bukbob_android.mainList_Module

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FoodListDataModel {

    private val db = Firebase.firestore

    data class FoodList(
        val List: ArrayList<*>,
        val Title: String,
        val Weeks: String,
        val state: String
    )

    suspend fun requestFoodListLunch(
        date:String,
        marketTitle:String,
        state:String,
        foodViewModel:FoodListViewModel
    ){
        var foodItem : FoodList? = null
        val marketName = checkNight(marketTitle,state)
        val docRef = db.collection(marketName).document(date)

        withContext(Dispatchers.IO) {
            var foodList : ArrayList<*>
            try {
                val document = docRef.get().await()
                if (document != null) {
                    foodList = if(document.data!!["List"] is String){
                        arrayListOf(document.data!!["List"])
                    }else{
                        document.data!!["List"] as ArrayList<*>
                    }

                    foodItem = FoodList(
                        foodList ,
                        document.data!!["Title"].toString(),
                        document.data!!["Weeks"].toString(),
                        document.data!!["state"].toString()
                    )
                }
                foodViewModel.foodItemLunch.postValue(foodItem)
            } catch (e: Exception) {
                foodViewModel.foodItemLunch.postValue(null)
            }
        }
    }

    /**
     * 각 식당의 점심 식단 리스트를 받아오는 함수입니다.
     * 코루틴이 적용되었습니다.
     * */

    suspend fun requestFoodListDinner(
        date:String,
        marketTitle:String,
        state:String,
        foodViewModel:FoodListViewModel
    ){
        var foodItem : FoodList? = null
        val marketName = checkNight(marketTitle,state)
        val docRef = db.collection(marketName).document(date)
        withContext(Dispatchers.IO) {
            var foodList : ArrayList<*>
            try {
                val document = docRef.get().await()
                if (document != null) {
                    foodList = if(document.data!!["List"] is String){
                        arrayListOf(document.data!!["List"])
                    }else{
                        document.data!!["List"] as ArrayList<*>
                    }

                    foodItem = FoodList(
                        foodList ,
                        document.data!!["Title"].toString(),
                        document.data!!["Weeks"].toString(),
                        document.data!!["state"].toString()
                    )
                }
                foodViewModel.foodItemDinner.postValue(foodItem)
            } catch (e: Exception) {
                foodViewModel.foodItemDinner.postValue(null)
            }
        }
    }

    /**
     * 각 식당의 저녁 식단 리스트를 받아오는 함수입니다.
     * 코루틴이 적용되었습니다.
     * */


    private fun checkNight(marketTitle : String, state:String) : String{
        return if(state == "night"){
            "${marketTitle}-${state}"
        }else{
            marketTitle
        }
    }
}