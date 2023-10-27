package mx.irisoft.pruebatecniva.presentation.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import mx.irisoft.pruebatecniva.databinding.FragmentMoviesBinding
import mx.irisoft.pruebatecniva.presentation.base.FragmentBase

class MoviesFragment : FragmentBase() {

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setListeners() {
        
    }

    override fun setObservers() {
        
    }

    override fun removeObservers() {
        
    }

    override fun initViewComponents() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}