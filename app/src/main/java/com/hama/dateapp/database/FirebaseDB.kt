package com.hama.dateapp.database

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hama.dateapp.model.UserInfo

object FirebaseDB {
    private val TAG:String="FireBaseDB"

    fun getUserInfo(): UserInfo? {
        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            return null
        } else {
            // Create a reference to the cities collection
            val Ref = db.collection("User")

            // Create a query against the collection.
            val query = Ref.whereEqualTo("uid", user?.uid)
            var userInfo: UserInfo? = null
            query.get()
                .addOnSuccessListener {
                    for (document in it) {
                        userInfo = UserInfo(document["name"].toString())
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG,"getUserInfo_Data가져오기 실패")
                }
            return userInfo
        }
    }}