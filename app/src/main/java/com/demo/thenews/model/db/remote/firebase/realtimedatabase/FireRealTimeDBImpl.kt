package com.demo.thenews.model.db.remote.firebase.realtimedatabase

import com.demo.thenews.view.base.FireRealTimeDB
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

private const val TAG = "xxxFireRealTimeDBImpl"

class FireRealTimeDBImpl @Inject constructor(databaseReference: DatabaseReference) :
    FireRealTimeDB(databaseReference) {

    private lateinit var messagesRef: DatabaseReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var messagesQuery: Query
    private lateinit var messagesListener: ValueEventListener
    private lateinit var messagesQueryListener: ChildEventListener

    private fun getMultipleInstances() {
        val primary = Firebase.database.reference
    }
    fun initDB() {
        databaseReference = Firebase.database.reference
    }
}
