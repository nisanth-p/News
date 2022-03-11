package com.demo.thenews.view.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.demo.thenews.R
import com.demo.thenews.databinding.FragmentHomeBinding
import com.demo.thenews.databinding.FragmentSigninBinding
import com.demo.thenews.model.util.BasicFunction
import com.demo.thenews.model.util.DebugMode
import com.demo.thenews.view.base.BaseFragment
import com.demo.thenews.view.ui.signin.SignInViewModel
import com.demo.thenews.view.ui.splash.TypeOfData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates
@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), CoroutineScope {

    companion object {
        fun newInstance() = HomeFragment()
    }
    private var globalLaunch: Job? = null
    private var globalLaunch1: Job? = null
    private var globalLoaderLaunch: Job? = null
    val mapHomeData = hashMapOf<String, String>()
    private var nextScreenId by Delegates.notNull<Int>()
    private var previousScreenId by Delegates.notNull<Int>()
    private var _viewBinding: FragmentHomeBinding? = null
    val viewModel by viewModels<HomeViewModel>()


    override fun setup() {
        _viewBinding = binding

        viewModel.init {
            when (it) {
                TypeOfData.INT -> {
                    nextScreenId =
                        BasicFunction.getScreens()["home.nextPage"] as Int
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

    }
    private fun bindViews() {
        if (DebugMode.isDebug(requireContext())) {

        }}
    override fun layoutRes(): Int=R.layout.fragment_home
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

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
}