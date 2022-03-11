package com.demo.thenews.view.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.demo.thenews.R
import com.demo.thenews.model.util.BasicFunction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment<VB : ViewBinding> : Fragment() {


    lateinit var list: Any
    val sharedModel: BaseViewModel by activityViewModels()
    private val _sharedModel get() = sharedModel

    private lateinit var navController: NavController
    protected lateinit var navHostFragment: NavHostFragment
    val FRAGMENT_NAME = "FRAGMENT_NAME"
    val FRAGMENT_VALUE = "FRAGMENT_VALUE"
    private var _binding: ViewBinding? = null


    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    abstract fun setup()

    @LayoutRes
    abstract fun layoutRes(): Int

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    fun nav(id: Int, bundle: Bundle = Bundle.EMPTY) = findNavController().navigate(id, bundle)
    private fun findNavControl() =
        (requireActivity() as NavigationHost).findNavControl()

    protected fun hideNavigation(animate: Boolean = true) =
        (requireActivity() as NavigationHost).hideNavigation(animate)

    protected fun showNavigation(animate: Boolean = true) =
        (requireActivity() as NavigationHost).showNavigation(animate)

    fun View.showSnackbar(
        view: View,
        msg: String,
        length: Int,
        actionMessage: CharSequence?,
        action: (View) -> Unit,
        action2: (View) -> Unit
    ) {
        val snackbar = Snackbar.make(view, msg, length)
        if (actionMessage != null) {
            snackbar.setAction(actionMessage) {
                action2(this)
            }.show()
        } else {
            snackbar.show()
        }
        action(this)
    }

    override fun onResume() {
        super.onResume()
        setupBackPressed()
    }

    protected open fun setupBackPressed() {
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(viewLifecycleOwner) {
            isEnabled = false
            findNavControl()?.run {
                when (currentDestination?.id) {
                    R.id.signInFragment,R.id.homeFragment -> {
                        showWhatsNewDialog(this@addCallback)
                        }
                    else -> {
                        remove()
                        onBackPressed()
                    }
                }
            }
        }

    }

    fun onBackPressed() {
        if (!navController.popBackStack()) {
            activity?.finish()
        }    }

    private fun showWhatsNewDialog(onBackPressedCallback: OnBackPressedCallback) {
        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
            .setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_dialog))
            .setView(R.layout.layout_empty_box_msg)
            .setCancelable(false)
            .setMessage("Are you want to exit the app?")
            .setPositiveButton(R.string.textYes) { _, _ ->
                onBackPressedCallback.remove()
                activity?.finish()
            }
            .setNegativeButton(R.string.textCancel) { _, _ ->
                nav(BasicFunction.getScreens()["signin_to_signin"] as Int)

            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

