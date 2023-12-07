package mx.irisoft.pruebatecniva.presentation.main.movies

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.data.remote.state.StatusType
import mx.irisoft.pruebatecniva.databinding.FragmentMoviesBinding
import mx.irisoft.pruebatecniva.presentation.base.FragmentBase
import mx.irisoft.pruebatecniva.presentation.main.MainActivity
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.setUpScrollingListener

@AndroidEntryPoint
class MoviesFragment : FragmentBase() {

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var activity: MainActivity
    private lateinit var adapter: MoviesAdapter
    private var nextPage = 1
    private var limit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPopularMovies(nextPage)
    }

    override fun setListeners() {
        with(binding) {
            actRv.setUpScrollingListener(requireContext(), adapter, true) { isLast, _ ->
                if (isLast && !limit) {
                    viewModel.getPopularMovies(nextPage)
                }
            }
        }
    }

    override fun setObservers() {
        with(viewModel) {
            responseMovies.observe(viewLifecycleOwner) {
                val resource = it ?: return@observe
                activity.showLoading(false)
                when (resource.statusType) {
                    StatusType.SUCCESS -> {
                        resource.data?.let { data ->
                            nextPage++
                            if (nextPage > data.totalPages) {
                                limit = true
                            }
                            adapter.addItemsAfter(data.results)
                            Handler(Looper.getMainLooper()).postDelayed(
                                {
                                    viewModel.getPopularMovies(nextPage)
                                },
                                5000L,
                            )
                        }
                    }
                    StatusType.ERROR -> activity.showInfoMessage(getString(R.string.title_error), resource.message)
                    StatusType.LOADING -> activity.showLoading(true)
                }
            }
        }
    }

    override fun removeObservers() {
        with(viewModel) {
            responseMovies.removeObservers(viewLifecycleOwner)
        }
    }

    override fun initViewComponents() {
        activity = requireActivity() as MainActivity
        adapter = MoviesAdapter(
            arrayListOf(),
            onTouchItem = { imagePath ->
                activity.showImageMessage(imagePath)
            },
            onFavoriteClick = { movie ->
                viewModel.addMovieToFavorites(movie)
                activity.showToastMessage(String.format(getString(R.string.toast_movie_added), movie.title))
            },
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
