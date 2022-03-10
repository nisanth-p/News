package com.demo.thenews.view.base


import com.google.firebase.firestore.*

import java.util.concurrent.*

abstract class FireStoreDatabase(val db: FirebaseFirestore) {

    companion object {

        private const val TAG = "xxxFireStoreDatabase"

        private val EXECUTOR = ThreadPoolExecutor(
            2, 4,
            60, TimeUnit.SECONDS, LinkedBlockingQueue()
        )
    }

}