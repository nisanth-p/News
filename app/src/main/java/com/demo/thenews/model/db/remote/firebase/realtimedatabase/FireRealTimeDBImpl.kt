package com.demo.thenews.model.db.remote.firebase.realtimedatabase

import android.util.Log
import com.demo.thenews.model.data.firebasemodel.User
import com.demo.thenews.view.base.FireRealTimeDB
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.lang.reflect.Type
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

    fun writeNewDeviceConfigureWithTaskListeners(
        childMap: HashMap<String, Any>,
        dataMap: HashMap<String, Any> = mutableMapOf<String, Any>() as HashMap<String, Any>,
        childList: ArrayList<User> = emptyList<Any>() as ArrayList<User>,
        type: String,
        function: (res: Any,code:Int) -> Unit
    ) {
        val resList = ArrayList<Any>()
        when (type) {
            "1" -> {
                messagesRef = database.child("app")
                    .child(childMap["child2"] as String)
                    .child(childMap["child3"] as String)
                    .child(childMap["child4"] as String)
                // .child(childMap["child5"].toString())
                messagesRef.setValue(childList)
                    .addOnSuccessListener {
                        messagesListener = object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                Log.d(TAG, "Number of messages: ${dataSnapshot.childrenCount}")
                                Log.d(
                                    TAG, "Number of data: ${
                                        dataSnapshot.children.filter {
                                            Log.d(
                                                TAG,
                                                "onDataChange: key = ${it.key} || value = ${it.value} "
                                            )
                                            true
                                        }
                                    }"
                                )
                                dataSnapshot.children.forEach { child ->
                                    child.key?.let { key ->
                                        child.value?.let { value ->
                                            //  resMap[key.toInt()] = value
                                            resList.add(value)
                                        }

                                    }
                                }
                                val gson = Gson()
                                var str = ""
                                val gsonType: Type =
                                    object : TypeToken<java.util.ArrayList<*>?>() {}.type
                                str = gson.toJson(resList, gsonType)
                                function(str,200)
                                //  function("response complete")
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Could not successfully listen for data, log the error
                                Log.e(TAG, "messages:onCancelled: ${error.message}")
                                function(error.message,400)
                            }
                        }
                        messagesRef.addValueEventListener(messagesListener)
                    }
                    .addOnFailureListener {
                        it.message?.let { ex ->
                            Timber.d(
                                "writeNewDeviceConfigureWithTaskListeners: "
                            )
                            function(ex,400)
                        }

                    }
            }
        }
    }
}
