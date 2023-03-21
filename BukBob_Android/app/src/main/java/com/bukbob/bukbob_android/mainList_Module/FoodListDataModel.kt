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

    suspend fun requestFoodList(
        date:String,
        marketTitle:String,
        state:String,
        foodViewModel:FoodListViewModel
    ){
        var foodItem : FoodList? = null
        val marketName = checkNight(marketTitle,state)
        val docRef = db.collection(marketName).document(date)

        withContext(Dispatchers.IO) {
            try {
                val document = docRef.get().await()
                if (document != null) {
                    foodItem = FoodList(
                        document.data!!["List"] as ArrayList<*> ,
                        document.data!!["Title"].toString(),
                        document.data!!["Weeks"].toString(),
                        document.data!!["state"].toString()
                    )
                }
                foodViewModel.foodItem.postValue(foodItem)
            } catch (e: Exception) {
                foodViewModel.foodItem.postValue(null)
            }
        }
    }

    private fun checkNight(marketTitle : String, state:String) : String{
        return if(state == "night"){
            "${marketTitle}-${state}"
        }else{
            marketTitle
        }
    }
}