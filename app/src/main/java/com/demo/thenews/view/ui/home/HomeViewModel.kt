package com.demo.thenews.view.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.demo.thenews.model.db.remote.firebase.firestore.FireStoreImpl
import com.demo.thenews.model.db.repository.CommonRepository
import com.demo.thenews.model.util.NetworkHelper
import com.demo.thenews.model.util.impl.IPref
import com.demo.thenews.view.base.BaseViewModel
import com.demo.thenews.view.ui.splash.TypeOfData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle,
                                        private val commonRepository: CommonRepository,
                                        private val networkHelper: NetworkHelper
): BaseViewModel(networkHelper) {
    private var list = mutableListOf<Any?>()
    private lateinit var doc: DocumentReference
    @Inject
    lateinit var db: FirebaseFirestore

    @Inject
    override lateinit var sharedPref: IPref

    @Inject
    lateinit var fireStoreImpl: FireStoreImpl
    fun init(function: (TypeOfData) -> Unit) {
        viewModelScope.launch {

        }

    }
}