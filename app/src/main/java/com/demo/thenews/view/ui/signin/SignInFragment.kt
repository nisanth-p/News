package com.demo.thenews.view.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.demo.thenews.view.ui.splash.TypeOfData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.thenews.R
import com.demo.thenews.databinding.FragmentSigninBinding
import com.demo.thenews.model.util.BasicFunction
import com.demo.thenews.model.util.DebugMode
import com.demo.thenews.model.util.ReqMapKey
import com.demo.thenews.model.util.SharedPrefKey
import com.demo.thenews.model.util.responsehelper.Status
import com.demo.thenews.view.base.BaseFragment
import com.google.android.material.snackbar.Snackbar


private const val TAG = "xxSignInFragment"

@DelicateCoroutinesApi
@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSigninBinding>(), CoroutineScope {
    private var tie_mailObs: MutableLiveData<String> = MutableLiveData()
    val tie_mailObs_: LiveData<String> = tie_mailObs

    private var tie_passwordObs: MutableLiveData<String> = MutableLiveData()
    val tie_passwordObs_: LiveData<String> = tie_passwordObs

    private var globalLaunch: Job? = null
    private var globalLaunch1: Job? = null
    private var globalLoaderLaunch: Job? = null
    val mapSigninData = hashMapOf<String, String>()
    private var nextScreenId by Delegates.notNull<Int>()
    private var previousScreenId by Delegates.notNull<Int>()
    private var _viewBinding: FragmentSigninBinding? = null
    val viewModel by viewModels<SignInViewModel>()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSigninBinding
        get() = FragmentSigninBinding::inflate

    override fun setup() {
        _viewBinding = binding

        viewModel.init {
            when (it) {
                TypeOfData.INT -> {
                   nextScreenId =
                        BasicFunction.getScreens()["signin.nextPage"] as Int
                    previousScreenId =
                        BasicFunction.getScreens()["signin.previousPage"] as Int
                }
                else -> {
                    error("Screen Not Available")
                }
            }
        }
        bindViews()
        onClickListeners()
    }

    private fun onClickListeners() {
        _viewBinding!!.tvSignin.setOnClickListener {
            Log.d(TAG, "onClickListeners: nextScreenId =$nextScreenId")

            globalLaunch = GlobalScope.launch(Dispatchers.Main) {
               launch {
                    globalLoaderLaunch = launch(coroutineContext) {
                        _viewBinding!!.lazyLoad.visibility = View.VISIBLE
                    }
                    if (mapSigninData[ReqMapKey.mail]?.isNotEmpty() == true &&
                        mapSigninData[ReqMapKey.mail]?.isNotBlank() == true &&
                        mapSigninData[ReqMapKey.password]?.isNotBlank() == true &&
                        mapSigninData[ReqMapKey.password]?.isNotEmpty() == true) {
                        _viewBinding!!.TILEmail.isFocusable = false
                        _viewBinding!!.TILPassword.isFocusable = false
                        _viewBinding!!.tvSignin.isClickable = false
                        _viewBinding!!.tvSignin.isFocusable = false

                        _viewBinding!!.TILEmail.error = null
                        _viewBinding!!.TILPassword.error = null
                        viewModel.postLogin(
                            user_email = mapSigninData[ReqMapKey.mail]!!,
                            user_password = mapSigninData[ReqMapKey.password]!!
                        )
                    }
                    if (mapSigninData[ReqMapKey.mail].isNullOrEmpty()) {
                        _viewBinding!!.TILEmail.error = "Mail id needed"
                        _viewBinding!!.TILEmail.isFocusable = true
                        return@launch
                    }
                    if (mapSigninData[ReqMapKey.password].isNullOrEmpty()) {
                        _viewBinding!!.TILPassword.error = "Password needed"
                        _viewBinding!!.TILPassword.isFocusable = true
                        return@launch
                    }
                }
            }
        }


        _viewBinding!!.IMBackArrow.setOnClickListener {
            nav(previousScreenId)
        }
    }

    private fun bindViews() {
        if (DebugMode.isDebug(requireContext())) {
            _viewBinding!!.TIEMailid.setText("abiletechopensource@gmail.com")
            //_viewBinding!!.TIEPhonenumber.setText("9988776655")
            _viewBinding!!.TIEPassword.setText("45rt45")
        }

        viewModel.loading_.observe(viewLifecycleOwner) {
            MainScope().launch(Dispatchers.Main) {
                if (it) {
                    _viewBinding!!.lazyLoad.visibility = View.VISIBLE
                } else {
                    _viewBinding!!.lazyLoad.visibility = View.GONE
                }
            }
        }

        /**
         * Register Data Res Observer
         */
        viewModel.res.observe(this) {
            _viewBinding!!.lazyLoad.visibility = View.GONE
            _viewBinding!!.IMBackArrow.visibility = View.VISIBLE
            _viewBinding!!.tvForgetPassword.visibility = View.VISIBLE
            _viewBinding!!.tvSignin.isClickable = true
            _viewBinding!!.tvSignin.isFocusable = true

            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        if (res?.success == true) {
                            _viewBinding!!.FLParentLayout.showSnackbar(
                                _viewBinding!!.FLParentLayout,
                                res.message!!,
                                Snackbar.LENGTH_SHORT,
                                "SUCCESS", {
                                    res.data?.filter { dataItem ->
                                        dataItem?.userAdminid?.let { userid ->
                                            if (!userid.isEmpty()) { }
                                        }
                                        dataItem?.userPhone?.let { userphone ->
                                            if (!userphone.isEmpty()) {  }
                                        }

                                        true
                                    }
                                    nav(nextScreenId)
                                }
                            ) {

                                Log.i(TAG, "bindViews: click toast")
                            }

                        } else {
                            if (res != null) {
                                _viewBinding!!.FLParentLayout.showSnackbar(
                                    _viewBinding!!.FLParentLayout,
                                    res.message!!,
                                    Snackbar.LENGTH_SHORT,
                                    "FAILED", {

                                    }
                                ) {
                                    Log.i(TAG, "bindViews: click toast")
                                }
                            }
                        }
                    }
                }
                Status.LOADING -> {
                    _viewBinding!!.tvForgetPassword.visibility = View.GONE
                    _viewBinding!!.lazyLoad.visibility = View.VISIBLE
                    _viewBinding!!.IMBackArrow.visibility = View.GONE

                    _viewBinding!!.tvSignin.isClickable = false
                    _viewBinding!!.tvSignin.isFocusable = false

                }
                Status.ERROR -> {
                    _viewBinding!!.FLParentLayout.showSnackbar(
                        _viewBinding!!.FLParentLayout,
                        it.message.toString(),
                        Snackbar.LENGTH_SHORT,
                        "ERROR", {
                            Log.i(TAG, "bindViews: NextFragment")
                        }
                    ) {
                        Log.i(TAG, "bindViews: click toast")
                    }

                }
            }
        }

        /**
         * Send Otp Res Observer
         */

        viewModel.otp_res.observe(this) {
            _viewBinding!!.lazyLoad.visibility = View.GONE
            _viewBinding!!.IMBackArrow.visibility = View.VISIBLE
            _viewBinding!!.tvForgetPassword.visibility = View.VISIBLE
            _viewBinding!!.tvSignin.isClickable = true
            _viewBinding!!.tvSignin.isFocusable = true

            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        if (res?.success == true) {
                            _viewBinding!!.FLParentLayout.showSnackbar(
                                _viewBinding!!.FLParentLayout,
                                res.message!!,
                                Snackbar.LENGTH_SHORT,
                                "SUCCESS", {
                                    Log.i(TAG, "bindViews: NextFragment")
                                }
                            ) {

                                Log.i(TAG, "bindViews: click toast")
                            }


                        } else {
                            if (res != null) {
                                _viewBinding!!.FLParentLayout.showSnackbar(
                                    _viewBinding!!.FLParentLayout,
                                    res.message!!,
                                    Snackbar.LENGTH_SHORT,
                                    "FAILED", {
                                        Log.i(TAG, "bindViews: NextFragment")
                                    }
                                ) {
                                    Log.i(TAG, "bindViews: click toast")
                                }
                            } else {
                                Log.i(TAG, "bindViews: res == null")
                            }

                        }
                    }
                }
                Status.LOADING -> {
                    _viewBinding!!.tvForgetPassword.visibility = View.GONE
                    _viewBinding!!.lazyLoad.visibility = View.VISIBLE
                    _viewBinding!!.IMBackArrow.visibility = View.GONE
                    _viewBinding!!.tvSignin.isClickable = false
                    _viewBinding!!.tvSignin.isFocusable = false

                }
                Status.ERROR -> {
                    _viewBinding!!.FLParentLayout.showSnackbar(
                        _viewBinding!!.FLParentLayout,
                        it.message.toString(),
                        Snackbar.LENGTH_SHORT,
                        "ERROR", {
                            Log.i(TAG, "bindViews: NextFragment")
                        }
                    ) {
                        Log.i(TAG, "bindViews: click toast")
                    }

                }
            }
        }


        /**
         * EMail
         */
        tie_mailObs.value = _viewBinding!!.TIEMailid.text.toString()
        _viewBinding!!.TILEmail.apply {
            // boxStrokeColor = Color.parseColor("#E68A06")
            boxStrokeColor =
                resources.getColor(R.color.purple_200, context.theme)
            boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
            setHintTextAppearance(R.style.ValidatableInputLayoutStyle_OutlineBox_HintInputLayoutStyle)
            setBoxCornerRadii(16f, 16f, 16f, 16f)
            setPadding(4, 0, 0, 0)
            setStartIconTintList(
                ContextCompat.getColorStateList(
                    context,
                    R.color.purple_500
                )
            )

        }.also { tie ->
            viewModel.tv_email_id_helper_.observe(this) {
                tie.helperText = it.toString()
            }
            viewModel.tv_email_id_.observe(this) {
                tie.hint = it.toString()
            }

        }
        _viewBinding!!.TIEMailid.addTextChangedListener {
            tie_mailObs.value = it.toString()
            sharedModel.sharedPref.put(SharedPrefKey.APP_USERMAIL, it.toString())
            if (it.toString().length >= 5) {
                _viewBinding!!.TILEmail.isFocusable = true
                _viewBinding!!.TILEmail.error = null
                _viewBinding!!.tvSignin.isClickable = true
                _viewBinding!!.tvSignin.isFocusable = true
            } else {
                _viewBinding!!.TILEmail.error = "Mail id needed"
                _viewBinding!!.TILEmail.isFocusable = true
                _viewBinding!!.tvSignin.isClickable = false
                _viewBinding!!.tvSignin.isFocusable = false
                _viewBinding!!.lazyLoad.visibility = View.GONE
            }
        }

        tie_mailObs_.observe(this) {
            viewModel.sharedPref.put(SharedPrefKey.APP_USERMAIL, it.toString())
            mapSigninData[ReqMapKey.mail] = it.toString()
        }
        /**
         * Password
         */
        tie_passwordObs.value = _viewBinding!!.TIEPassword.text.toString()
        _viewBinding!!.TILPassword.apply {
            boxStrokeColor =
                resources.getColor(R.color.purple_200, context.theme)
            boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
            setHintTextAppearance(R.style.ValidatableInputLayoutStyle_OutlineBox_HintInputLayoutStyle)
            setBoxCornerRadii(16f, 16f, 16f, 16f)
            setPadding(4, 0, 0, 0)
            setStartIconTintList(
                ContextCompat.getColorStateList(
                    context,
                    R.color.purple_500
                )
            )
        }.also { tie ->
            viewModel.tv_password_helper_.observe(this) {
                tie.helperText = it.toString()
            }
            viewModel.tv_password_.observe(this) {
                tie.hint = it.toString()
            }
        }

        _viewBinding!!.TIEPassword.addTextChangedListener {
            if (it.toString().length > 3) {
                tie_passwordObs.value = it.toString()
                _viewBinding!!.TILPassword.isFocusable = true
                _viewBinding!!.TILPassword.error = null
                _viewBinding!!.tvSignin.isClickable = true
                _viewBinding!!.tvSignin.isFocusable = true
            } else {
                _viewBinding!!.TILPassword.error = "Mail id needed"
                _viewBinding!!.TILPassword.isFocusable = true
                _viewBinding!!.tvSignin.isClickable = false
                _viewBinding!!.tvSignin.isFocusable = false
                _viewBinding!!.lazyLoad.visibility = View.GONE

            }
        }

        tie_passwordObs_.observe(this) {
            mapSigninData[ReqMapKey.password] = it.toString()
        }

        viewModel.tv_forgetpassword_id_.observe(this) { str ->
            _viewBinding!!.tvForgetPassword.apply {
                val content = SpannableString(str)
                content.setSpan(UnderlineSpan(), 0, str.toString().length, 0)
                text = content
            }
        }

        viewModel.bt_signin_.observe(this) {
            _viewBinding!!.tvSignin.text = it.toString()
        }
    }

    override fun layoutRes(): Int = R.layout.fragment_signin


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle()
            }
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        globalLaunch?.cancel()
        globalLoaderLaunch?.cancel()
        _viewBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}