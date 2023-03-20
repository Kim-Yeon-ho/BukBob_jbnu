package com.bukbob.bukbob_android.mainList_Module

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList


class FoodListDataController {

    val db = Firebase.firestore
    data class FoodList(
        val List: ArrayList<Any>,
        val Title: String,
        val Weeks: String,
        val state: String
    )
    var foodArray = ArrayList<FoodList>()
    var weeks = arrayListOf<String>("월","화","수","목","금")
    var count = 0
    fun getFoodListData() {
        for(i in 0 until 5){
            getData(i)
        }
    }

    fun getData(i : Int){
        val docRef = db.collection("Jinswo").document(weeks[i])
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var foodItem = FoodList(
                        document.data!!.get("List") as ArrayList<Any>,
                        document.data!!.get("Title").toString(),
                        document!!.data!!.get("Weeks").toString(),
                        document!!.data!!.get("state").toString()
                    )
                    foodArray.add(foodItem)
                } else {
                    Log.d("테스트", "No such document")
                }
                count ++
            }
            .addOnFailureListener { exception ->
                Log.d("테스트", "get failed with ", exception)
            }.addOnSuccessListener {
                if(count == 4){
                    Log.d("테스트",foodArray.get(2).List.get(1).toString())
                }
            }
    }



}
