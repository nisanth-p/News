package com.demo.thenews.model.db.remote.firebase.firestore
import com.demo.thenews.view.base.FireStoreDatabase
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

private const val TAG = "xxxFireStoreImpl"
class FireStoreImpl @Inject constructor(db: FirebaseFirestore) : FireStoreDatabase(db) {
    private lateinit var lists: List<HashMap<String, Any?>>
}