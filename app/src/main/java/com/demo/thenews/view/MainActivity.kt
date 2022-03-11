package com.demo.thenews.view

import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.demo.thenews.R
import com.demo.thenews.databinding.ActivityMainBinding
import com.demo.thenews.view.base.BaseActivity
import com.demo.thenews.view.base.NavigationHost
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() , NavigationHost {
    private var viewBinding: ActivityMainBinding? = null

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        viewBinding = binding
        setupBackPressed()
        bindViews()
    }
    private fun bindViews() {
        // setSupportActionBar(viewBinding!!.toolbar)
    }
    private fun setupBackPressed() {
        val dispatcher = onBackPressedDispatcher
        dispatcher.addCallback(this) {
            isEnabled = false
            findNavControl()?.run {
                when (currentDestination?.id) {
                    R.id.signInFragment,R.id.homeFragment  -> {
                        showWhatsNewDialog(onBackPressedCallback = this@addCallback)
                    }
                    else -> {
                        remove()
                        super.onBackPressed()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setupBackPressed()
    }

    private fun showWhatsNewDialog(onBackPressedCallback: OnBackPressedCallback) {
        MaterialAlertDialogBuilder(this, R.style.AlertDialog)
            .setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dialog))
            .setView(R.layout.layout_empty_box_msg)
            .setCancelable(false)
            .setMessage("Are you want to exit the app?")
            .setPositiveButton(R.string.textYes) { _, _ ->
                onBackPressedCallback.remove()
                finish()
            }
            .setNegativeButton(R.string.textCancel) { _, _ -> }
            .show()
    }


    override fun findNavControl(): NavController? =findNavHostFragment()?.findNavController()

    override fun hideNavigation(animate: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showNavigation(animate: Boolean) {
        TODO("Not yet implemented")
    }

    override fun openTab(navigationId: Int) {
        TODO("Not yet implemented")
    }

    override fun openDiscoverTab() {
        TODO("Not yet implemented")
    }

}

