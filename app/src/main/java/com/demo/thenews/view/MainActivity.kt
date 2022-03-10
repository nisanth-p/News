package com.demo.thenews.view

import android.view.LayoutInflater
import com.demo.thenews.R
import com.demo.thenews.databinding.ActivityMainBinding
import com.demo.thenews.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private  var binding: ActivityMainBinding?=null

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        binding = viewBinding
       /* val navController: NavController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()!!*/

    }
}

