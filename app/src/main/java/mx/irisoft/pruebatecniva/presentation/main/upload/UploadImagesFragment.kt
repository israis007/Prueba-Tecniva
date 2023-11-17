package mx.irisoft.pruebatecniva.presentation.main.upload

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.yalantis.ucrop.UCrop
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.data.remote.state.StatusType
import mx.irisoft.pruebatecniva.databinding.FragmentUploadImagesBinding
import mx.irisoft.pruebatecniva.presentation.base.FragmentBase
import mx.irisoft.pruebatecniva.presentation.main.MainActivity
import java.io.File

private const val TAG = "UploadImages"
class UploadImagesFragment() : FragmentBase() {

    private var _binding: FragmentUploadImagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: UploadImagesViewModel by viewModels()
    private lateinit var activity: MainActivity
    private lateinit var adapter: UploadImagesAdapter
    private var listUrls = ArrayList<String>()
    private var listImagesFB = ArrayList<String>()

    // Var To catchResultUcrop
    private val ucropResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK && result.data != null) {
            val resultUri: Uri? = UCrop.getOutput(result.data!!)
            // Maneja la URI del resultado
            if (resultUri != null) {
                viewModel.uploadImage(resultUri)
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(result.data!!)
            // Maneja el error
            activity.showInfoMessage(getString(R.string.title_error), "${getString(R.string.err_no_image_choose)} ${cropError?.message}")
        }
    }

    private val imageContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        Log.d(TAG, "Uri selected: $it")

        val destinationUri: Uri? = createImageUri() // Crea una URI de destino

        if (it != null && destinationUri != null) {

            requireContext().contentResolver.openOutputStream(destinationUri, "w")

            val options = UCrop.Options().apply {
                setCropFrameColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                setCropGridColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                setCompressionFormat(Bitmap.CompressFormat.JPEG)
                setCompressionQuality(63)
                setToolbarTitle(getString(R.string.ucrop_title))
            }

            val ucrop = UCrop.of(it, destinationUri)
                .withAspectRatio(1f, 1f)
                .withMaxResultSize(1080, 1080)
                .withOptions(options)
                .getIntent(requireContext())

            ucropResultLauncher.launch(ucrop)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUploadImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setListeners() {
        with(binding) {
            fabAdd.setOnClickListener {
                imageContract.launch("image/*")
            }
        }
    }

    override fun setObservers() {
        with(viewModel) {
            uploadSuccess.observe(viewLifecycleOwner) {
                val resp = it ?: return@observe
                when (resp.resource.statusType) {
                    StatusType.SUCCESS -> {
                        activity.showLoading(false)
                        viewModel.completeNotification()
                        viewModel.getListImages()
                    }
                    StatusType.ERROR -> {
                        activity.showLoading(false)
                        activity.showInfoMessage(getString(R.string.title_error), resp.resource.message)
                    }
                    StatusType.LOADING -> {
                        activity.showLoading(true)
                        viewModel.createNotification()
                        viewModel.updateNotification(resp.totalByteCount, resp.bytesTransferred)
                    }
                }
            }
            listImages.observe(viewLifecycleOwner) {
                val resource = it ?: return@observe
                when (resource.statusType) {
                    StatusType.SUCCESS -> {
                        listUrls = ArrayList()
                        listImagesFB = ArrayList<String>().apply {
                            addAll(resource.data ?: listOf())
                        }
                        listImagesFB.forEach { url ->
                            viewModel.getURLImage(url)
                        }
                    }

                    StatusType.ERROR -> activity.showInfoMessage(
                        getString(R.string.title_error),
                        resource.message,
                    )

                    StatusType.LOADING -> activity.showLoading(true)
                }
            }
            urlImage.observe(viewLifecycleOwner) {
                val resource = it ?: return@observe
                when (resource.statusType) {
                    StatusType.SUCCESS -> {
                        listUrls.add(resource.data ?: "")
                        if (listUrls.size == listImagesFB.size) {
                            activity.showLoading(false)
                            createAdapter()
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
            urlImage.removeObservers(viewLifecycleOwner)
            uploadSuccess.removeObservers(viewLifecycleOwner)
            listImages.removeObservers(viewLifecycleOwner)
        }
    }

    override fun initViewComponents() {
        activity = requireActivity() as MainActivity
        activity.checkAndRequestPermissions()
        createAdapter()
        binding.rvList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@UploadImagesFragment.adapter
        }
        viewModel.getListImages()
    }

    private fun createImageUri(): Uri? {
        val contentResolver = requireContext().contentResolver
        val name = "image_${System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, name)
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.AUTHOR, "Pirata Ram")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            // En Android 10 y versiones superiores, usa MediaStore.MediaColumns.RELATIVE_PATH
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "PruebaTecnica")
            }
        }

        return try {
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun createAdapter() {
        adapter = UploadImagesAdapter(listUrls) {
            activity.showImageMessage(it)
        }
        binding.rvList.adapter = adapter
        binding.rvList.adapter?.notifyDataSetChanged()
    }
}