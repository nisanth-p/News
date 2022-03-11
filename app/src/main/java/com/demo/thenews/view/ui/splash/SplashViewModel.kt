package com.demo.thenews.view.ui.splash

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.demo.thenews.model.db.repository.CommonRepository
import com.demo.thenews.model.util.BasicFunction
import com.demo.thenews.model.util.NetworkHelper
import com.demo.thenews.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

enum class TypeOfData {
    INT, STRING, ANY, ELSE
}

private const val TAG = "SplashViewModel"

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext context: Context, private val savedStateHandle: SavedStateHandle,
    private val commonRepository: CommonRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel(networkHelper){
    var screen: MutableLiveData<TypeOfData> = MutableLiveData()
    var _screen: LiveData<TypeOfData> = screen
    private val SPLASH_TIME_OUT: Long = 3000 // 1 sec
    fun init(function: (TypeOfData) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
                when (BasicFunction.getScreens()["splash_to_signin"]) {
                    is Int -> {
                        function(TypeOfData.INT)
                    }
                    else -> {
                        function(TypeOfData.ANY)
                    }
                }
           }, SPLASH_TIME_OUT)
    }
}