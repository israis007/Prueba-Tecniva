package mx.irisoft.pruebatecniva.presentation.main.persons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.data.remote.state.StatusType
import mx.irisoft.pruebatecniva.databinding.FragmentPersonsBinding
import mx.irisoft.pruebatecniva.presentation.base.FragmentBase
import mx.irisoft.pruebatecniva.presentation.main.MainActivity
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.setUpScrollingListener

@AndroidEntryPoint
class PersonsFragment : FragmentBase(){

    private var _binding: FragmentPersonsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: PersonsViewModel by viewModels()
    private lateinit var adapter: PersonsAdapter
    private lateinit var activity: MainActivity
    private var nextPage = 1
    private var limit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPopularPersons(nextPage)
    }

    override fun setListeners() {
        with(binding) {
            actRv.setUpScrollingListener(requireContext(), adapter, true) { lastItem, _ ->
                if (lastItem && !limit)
                    viewModel.getPopularPersons(nextPage)
            }
        }
    }

    override fun setObservers() {
        with(viewModel) {
            listPersons.observe(viewLifecycleOwner) { it ->
                val list = it ?: return@observe
                activity.showLoading(false)
                when(list.statusType) {
                    StatusType.SUCCESS -> {
                        list.data?.let { data ->
                            nextPage++
                            if (nextPage > data.totalPages)
                                limit = true
                            adapter.addItemsAfter(data.results)
                        }
                    }
                    StatusType.ERROR -> activity.showInfoMessage(getString(R.string.title_error), list.message)
                    StatusType.LOADING -> activity.showLoading(true)
                }
            }
        }
    }

    override fun removeObservers() {
        with(viewModel) {
            listPersons.removeObservers(viewLifecycleOwner)
        }
    }

    override fun initViewComponents() {
        activity = requireActivity() as MainActivity
        adapter = PersonsAdapter(requireContext(), arrayListOf(), onTouchElement = { pathImage: String ->
            activity.showImageMessage(pathImage)
        }, onChipClicked = { obj ->
            activity.showMovieMessage(obj)
        })
    }
}