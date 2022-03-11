package com.demo.thenews.view.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.demo.thenews.R
import com.demo.thenews.databinding.FragmentSplashBinding
import com.demo.thenews.model.util.BasicFunction
import com.demo.thenews.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

private const val TAG = "xxxSplashFragment"
@DelicateCoroutinesApi
@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private var _viewBinding: FragmentSplashBinding? = null
    val viewModel by viewModels<SplashViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate


    override fun setup() {
        _viewBinding = binding
        viewModel.init {
            when (it) {
                TypeOfData.INT ->  nav(BasicFunction.getScreens()["splash_to_signin"] as Int)
                else ->  error("Screen Not Available")
            }
        }


    }

    override fun layoutRes(): Int = R.layout.fragment_splash


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {
                    putString(FRAGMENT_NAME, param1)
                    putString(FRAGMENT_VALUE, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}