package mx.irisoft.pruebatecniva.presentation.main.notes

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mx.irisoft.pruebatecniva.databinding.FragmentNotesBinding
import mx.irisoft.pruebatecniva.presentation.base.FragmentBase
import mx.irisoft.pruebatecniva.presentation.main.MainActivity

@AndroidEntryPoint
class NotesFragment : FragmentBase() {

    private var _binding: FragmentNotesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by viewModels()
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