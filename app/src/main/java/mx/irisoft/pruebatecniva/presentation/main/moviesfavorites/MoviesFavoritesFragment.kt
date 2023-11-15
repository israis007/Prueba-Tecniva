package mx.irisoft.pruebatecniva.presentation.main.moviesfavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.databinding.FragmentMoviesFavoritesBinding
import mx.irisoft.pruebatecniva.presentation.base.FragmentBase
import mx.irisoft.pruebatecniva.presentation.main.MainActivity

@AndroidEntryPoint
class MoviesFavoritesFragment : FragmentBase() {

    private var _binding: FragmentMoviesFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MoviesFavoritesViewModel by viewModels()
    private lateinit var activity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = requireActivity() as MainActivity
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
//        activity.setSupportActionBar(toolbar)
    }

    override fun setListeners() {
        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //Se realiza la búsqueda
                    viewModel.searchmovie(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
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