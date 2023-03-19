package com.bukbob.bukbob_android.mainList_Module

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FoodListDataController {

    val db = Firebase.firestore
    var list = arrayListOf(arrayOf({}),arrayOf({}),arrayOf({}))
    fun getFoodListData() {
        db.collection("Food")
            .get()
            .addOnSuccessListener { result ->
                result.documents.get(0).get("FoodList").let {
                    Log.d("데이터",it.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w("데이터", "Error getting documents.", exception)
            }
    }

}