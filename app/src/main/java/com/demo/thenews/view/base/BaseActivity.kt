package com.demo.thenews.view.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private val sharedModel by viewModels<BaseViewModel>()
    val _sharedModel get() = sharedModel

    private var _viewBinding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val viewBinding: VB
        get() = _viewBinding as VB

    abstract fun setup()

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = bindingInflater.invoke(layoutInflater)
        setContentView(viewBinding.root)
        setup()
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}

