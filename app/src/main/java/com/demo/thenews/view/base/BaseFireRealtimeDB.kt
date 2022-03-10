package com.demo.thenews.view.base

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "xxxBaseFireRealtimeDB"

abstract class FireRealTimeDB(var database: DatabaseReference) {

    fun initializeDbRef() {
        database = Firebase.database.reference
    }

}