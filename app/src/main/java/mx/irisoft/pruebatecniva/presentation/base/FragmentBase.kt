package mx.irisoft.pruebatecniva.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class FragmentBase : Fragment() {

    abstract fun setListeners()
    abstract fun setObservers()
    abstract fun removeObservers()
    abstract fun initViewComponents()

    lateinit var activity: ActivityBase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = requireActivity() as ActivityBase
        initViewComponents()
        setListeners()
        setObservers()
    }

    override fun onDestroyView() {
        removeObservers()
        super.onDestroyView()
    }

    fun goToFragment(fragmentId: Int, bundle: Bundle? = null){
        findNavController().navigate(fragmentId, bundle)
    }
}