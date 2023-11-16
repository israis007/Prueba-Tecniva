package mx.irisoft.pruebatecniva.presentation.main.moviesfavorites

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.databinding.FragmentMoviesFavoritesBinding
import mx.irisoft.pruebatecniva.domain.enums.SearchType
import mx.irisoft.pruebatecniva.domain.models.MovieModel
import mx.irisoft.pruebatecniva.presentation.base.FragmentBase
import mx.irisoft.pruebatecniva.presentation.main.MainActivity
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.setUpScrollingListener

@AndroidEntryPoint
class MoviesFavoritesFragment : FragmentBase() {

    private var _binding: FragmentMoviesFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MoviesFavoritesViewModel by viewModels()
    private lateinit var activity: MainActivity
    private lateinit var adapter: MoviesFavoritesAdapter
    private var lastSearchType = SearchType.TITLE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesFavoritesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllMoviesLocal()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        for (i in 0 until menu.size) {
            val drawable = menu.getItem(i).icon
            drawable?.let{
                it.mutate()
                it.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(requireContext(), R.color.white), PorterDuff.Mode.SRC_ATOP)
            }
        }

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Realizar búsqueda
                viewModel.searchmovie(query ?: "", lastSearchType)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Actualizar búsqueda en tiempo real
                return true
            }
        })

        val filterItem = menu.findItem(R.id.action_filter)
        filterItem?.setOnMenuItemClickListener {
            activity.mostrarDialogoFiltro(false) {
                lastSearchType = it
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun setListeners() {
        with(binding) {
        }
    }

    override fun setObservers() {
        with(viewModel) {
            listMovies.observe(viewLifecycleOwner) {
                val list = it ?: return@observe
                createAdapter(list)
            }
        }
    }

    override fun removeObservers() {
    }

    override fun initViewComponents() {
        activity = requireActivity() as MainActivity
        createAdapter(arrayListOf())
    }

    private fun createAdapter(list: List<MovieModel>) {
        adapter = MoviesFavoritesAdapter(
            ArrayList<MovieModel>().apply {
                addAll(list)
            },
            onTouchItem = { pathImage ->
                activity.showImageMessage(pathImage)
            },
            onDeleteClick = { movie ->
                viewModel.deleteMovieLocal(movie)
                activity.showToastMessage(String.format(getString(R.string.toast_movie_deleted), movie.title))
            },
        )
        binding.actRv.adapter = adapter
        binding.actRv.adapter?.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}