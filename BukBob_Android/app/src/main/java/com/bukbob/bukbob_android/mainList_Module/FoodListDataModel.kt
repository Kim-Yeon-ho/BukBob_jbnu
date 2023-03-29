/**
 * 2023 / 03 / 24 LeeJungHwan 작성
 * */

package com.bukbob.bukbob_android.mainList_Module

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class FoodListDataModel {

    private val db = Firebase.firestore

    data class FoodList(
        val List: ArrayList<*>,
        val Title: String,
        val Weeks: String,
        val state: String
    )

    suspend fun requestFoodList(
        date:String,
        marketTitle:String,
        state:String,
        foodViewModel:FoodListViewModel
    ){
        var foodItem : FoodList? = null

        val marketName = if(marketTitle != "Husaeng"){
            "$marketTitle-$state"
        }else{
            marketTitle
        }

        val docRef = db.collection(marketName).document(date)
        withContext(Dispatchers.IO) {
            val foodList : ArrayList<*>
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
                when(state){
                    "breakfast" -> foodViewModel.breakFoodItem.postValue(foodItem)
                    "lunch" -> foodViewModel.lunchFoodItem.postValue(foodItem)
                    "night" -> foodViewModel.dinnerFoodItem.postValue(foodItem)
                    else -> foodViewModel.lunchFoodItem.postValue(foodItem)
                }
            } catch (e: Exception) {
                when(state){
                    "breakfast" -> foodViewModel.breakFoodItem.postValue(foodItem)
                    "lunch" -> foodViewModel.lunchFoodItem.postValue(foodItem)
                    "night" -> foodViewModel.dinnerFoodItem.postValue(foodItem)
                    else -> foodViewModel.lunchFoodItem.postValue(foodItem)
                }
            }
        }
    }

    /**
     * requestFoodListDinner()?
     * 각 식당의 저녁 식단 리스트를 받아오는 함수입니다.
     * 코루틴이 적용되었습니다.
     * */
}