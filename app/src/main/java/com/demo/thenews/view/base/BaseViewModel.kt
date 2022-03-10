package com.demo.thenews.view.base

import android.os.Build
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.demo.thenews.model.util.BasicFunction
import com.demo.thenews.model.util.NetworkHelper
import com.demo.thenews.model.util.impl.IPref
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "BaseViewModel"
@HiltViewModel
open class BaseViewModel @Inject constructor(private val networkHelper: NetworkHelper) :
    ViewModel(), DefaultLifecycleObserver {
    @Inject
    lateinit var basicFunction: BasicFunction

    @Inject
    open lateinit var sharedPref: IPref
    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var jsonObj: JSONObject

    @Inject
    lateinit var jsonArray: JSONArray
    lateinit var owner: LifecycleOwner

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.i(TAG, "onStart: LifecycleOwner")

    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        this.owner = owner
        Log.i(TAG, "onCreate: LifecycleOwner")

    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.i(TAG, "onResume: LifecycleOwner")

    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.i(TAG, "onStop: LifecycleOwner")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i(TAG, "onDestroy: LifecycleOwner")
    }

    private fun setup() {


    }




    companion object {
        @JvmStatic
        fun getPhoneDeviceName(): String {
            val model = Build.MODEL // returns model name
            return model
        }

        fun getTimeWithSec(
            millisUntilFinished: Long = 5 * 60000,
            needDay: Boolean = false,
            needMins: Boolean = false,
            needSec: Boolean = false
        ): String {
            val millis: Long = millisUntilFinished
            return when {
                needDay -> {
                    String.format(
                        "%02d:%02d:%02d:%02d",
                        TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toDays(millis)),
                        (TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(
                            TimeUnit.MILLISECONDS.toDays(
                                millis
                            )
                        )),
                        (TimeUnit.MILLISECONDS.toMinutes(millis) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))),
                        (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millis)
                        ))
                    )
                }
                needMins -> {
                    String.format(
                        "%02d:%02d",

                        (TimeUnit.MILLISECONDS.toMinutes(millis) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))),
                        (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millis)
                        ))
                    )

                }
                needSec -> {
                    String.format(
                        "%02d",
                        (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millis)
                        ))
                    )
                }
                else -> {
                    return ""
                }
            }

        }
    }

    fun onPostResume(fragName: String) {
        onpostresume.value = fragName
    }

    var login_user_name = ObservableField<String>("")
    var login_user_mail = ObservableField<String>("")
    var login_user_phone = ObservableField<String>("")
    var login_user_device_id = ObservableField<String>("")
    var onpostresume = MutableLiveData<String>()
    val onpostresume_: LiveData<String> = onpostresume
    var onAddRoom = MutableLiveData<JSONObject>()
    val onAddRoom_: LiveData<JSONObject> = onAddRoom

}