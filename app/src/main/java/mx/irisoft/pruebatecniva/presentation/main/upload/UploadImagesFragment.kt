package mx.irisoft.pruebatecniva.presentation.main.upload

import androidx.fragment.app.viewModels
import mx.irisoft.pruebatecniva.databinding.FragmentUploadImagesBinding
import mx.irisoft.pruebatecniva.presentation.base.FragmentBase
import mx.irisoft.pruebatecniva.presentation.main.MainActivity

class UploadImagesFragment: FragmentBase(){

    private var _binding: FragmentUploadImagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: UploadImagesViewModel by viewModels()
    private lateinit var activity: MainActivity

    override fun setListeners() {

    }

    override fun setObservers() {

    }

    override fun removeObservers() {

    }

    override fun initViewComponents() {

    }
}