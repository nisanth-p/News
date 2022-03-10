package com.demo.thenews.view.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.demo.thenews.model.data.appscript.AppScriptResponse
import com.demo.thenews.model.db.remote.firebase.firestore.FireStoreImpl
import com.demo.thenews.model.db.repository.CommonRepository
import com.demo.thenews.model.util.NetworkHelper
import com.demo.thenews.model.util.PostmanQueryParams
import com.demo.thenews.model.util.impl.IPref
import com.demo.thenews.model.util.responsehelper.Resource
import com.demo.thenews.view.base.BaseViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.demo.thenews.view.ui.splash.TypeOfData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "xxxSignInViewModel"
@HiltViewModel
class SignInViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle,
                                          private val commonRepository: CommonRepository,
                                          private val networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {

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

    suspend fun postLogin(user_id:String="",user_name:String="",user_email:String="",user_phone:String="",user_password:String=""){

        map[PostmanQueryParams.QUERY_KEY_ACTION_RUN] = PostmanQueryParams.QUERY_VALUE_POST
        map[PostmanQueryParams.QUERY_KEY_ACTION] = PostmanQueryParams.QUERY_VALUE_ACTION_LOGIN
        map[PostmanQueryParams.QUERY_KEY_DEVICE_ID] = user_id
        map[PostmanQueryParams.QUERY_KEY_NAME] = user_name
        map[PostmanQueryParams.QUERY_KEY_PHONE] = user_phone
        map[PostmanQueryParams.QUERY_KEY_MAIL] = user_email
        map[PostmanQueryParams.QUERY_KEY_PASSWORD] = user_password
        viewModelScope.launch(Dispatchers.IO) {
            _res.postValue(Resource.loading(null))
            try {
                commonRepository.postLogin(map).let {
                    if (it.isSuccessful){
                        _res.postValue(Resource.success(it.body()))
                    }else{
                        _res.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }catch (ex: Exception){
                _res.postValue(Resource.error(ex.message.toString(), null))

            }
        }

    }

    suspend fun postSendOtp(user_email:String="",user_phone:String=""){
        login_user_mail.set(user_email)
        map[PostmanQueryParams.QUERY_KEY_ACTION_RUN] = PostmanQueryParams.QUERY_VALUE_POST
        map[PostmanQueryParams.QUERY_KEY_ACTION] = PostmanQueryParams.QUERY_VALUE_ACTION_FORGET
        map[PostmanQueryParams.QUERY_KEY_PHONE] = user_phone
        map[PostmanQueryParams.QUERY_KEY_MAIL] = user_email

        viewModelScope.launch(Dispatchers.IO) {
            _otp_res.postValue(Resource.loading(null))
            try {
                commonRepository.postForgetPassword(map).let {
                    if (it.isSuccessful){
                        _otp_res.postValue(Resource.success(it.body()))
                    }else{
                        _otp_res.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }catch (ex: Exception){
                _otp_res.postValue(Resource.error(ex.message.toString(), null))

            }
        }
    }
    /**Field text*/
    private var tv_title: MutableLiveData<String> = MutableLiveData()
    val tv_title_: LiveData<String> = tv_title

    private var tv_forgetpassword_id: MutableLiveData<String> = MutableLiveData()
    val tv_forgetpassword_id_: LiveData<String> = tv_forgetpassword_id

    private var tv_email_id: MutableLiveData<String> = MutableLiveData()
    val tv_email_id_: LiveData<String> = tv_email_id

    private var tv_email_id_left_icon_color: MutableLiveData<String> = MutableLiveData()
    val tv_email_id_left_icon_color_: LiveData<String> = tv_email_id_left_icon_color

    private var tv_email_id_helper: MutableLiveData<String> = MutableLiveData()
    val tv_email_id_helper_: LiveData<String> = tv_email_id_helper

    private var tv_password: MutableLiveData<String> = MutableLiveData()
    val tv_password_: LiveData<String> = tv_password

    private var tv_password_left_icon_color: MutableLiveData<String> = MutableLiveData()
    val tv_password_left_icon_color_: LiveData<String> = tv_password_left_icon_color

    private var tv_password_helper: MutableLiveData<String> = MutableLiveData()
    val tv_password_helper_: LiveData<String> = tv_password_helper

    private var bt_signin: MutableLiveData<String> = MutableLiveData()
    val bt_signin_: LiveData<String> = bt_signin

    private var img_back_arrow: MutableLiveData<String> = MutableLiveData()
    val img_back_arrow_: LiveData<String> = img_back_arrow

    private var img_back_arrow_color: MutableLiveData<String> = MutableLiveData()
    val img_back_arrow_color_: LiveData<String> = img_back_arrow_color


    /**Enable Field*/
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading_: LiveData<Boolean> = loading
    var isLoadings: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadings_: LiveData<Boolean> = isLoadings

    private var tv_email_id_enable: MutableLiveData<Boolean> = MutableLiveData()
    val _tv_email_id_enable: LiveData<Boolean> = tv_email_id_enable

    private var tv_signin_enable: MutableLiveData<Boolean> = MutableLiveData()
    val _tv_signin_enable: LiveData<Boolean> = tv_signin_enable

    private var tv_password_enable: MutableLiveData<Boolean> = MutableLiveData()
    val _tv_password_enable: LiveData<Boolean> = tv_password_enable
    private var available_room_list_empty: MutableLiveData<Boolean> = MutableLiveData()
    val available_room_list_empty_: LiveData<Boolean> = available_room_list_empty
    /** Remote Response */

    private val _res = MutableLiveData<Resource<AppScriptResponse>>()

    val res : LiveData<Resource<AppScriptResponse>>
        get() = _res

    private val _otp_res = MutableLiveData<Resource<AppScriptResponse>>()

    val otp_res : LiveData<Resource<AppScriptResponse>>
        get() = _otp_res

    private var map= hashMapOf<String,String>()

}