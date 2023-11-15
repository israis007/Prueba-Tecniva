package mx.irisoft.pruebatecniva.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.data.remote.datasource.KnowFor
import mx.irisoft.pruebatecniva.databinding.DialogImageBinding
import mx.irisoft.pruebatecniva.databinding.DialogInfoBinding
import mx.irisoft.pruebatecniva.databinding.DialogMovieBinding
import mx.irisoft.pruebatecniva.utils.constants.ARG_EXTRAS

abstract class ActivityBase : AppCompatActivity() {

    private val dialogLoading by lazy {
        val dialogAlertDialog = AlertDialog.Builder(this, R.style.CustomDialog)
        dialogAlertDialog.setView(R.layout.dialog_loading)
        dialogAlertDialog.setCancelable(false)
        dialogAlertDialog.create()
    }

    fun showLoading(loading: Boolean) {
        if (loading) {
            if (!dialogLoading.isShowing)
                dialogLoading.show()
        } else {
            if (dialogLoading.isShowing)
                dialogLoading.dismiss()
        }
    }

    fun showInfoMessage(title: String, message: String, response: (isAccept: Boolean) -> Unit) {
        val dialog = AlertDialog.Builder(this@ActivityBase, R.style.CustomDialogInfo)
        val vm = ViewModelProvider(this@ActivityBase)[DialogViewModel::class.java]
        val binding: DialogInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@ActivityBase),
            R.layout.dialog_info,
            null,
            false
        )
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@ActivityBase
        }
        dialog.setView(binding.root)
        dialog.setCancelable(false)
        dialog.setPositiveButton(R.string.general_accept) { dial, _ ->
            response(true)
            dial.dismiss()
        }
        vm.setInfo(title, message)
        dialog.create().show()
    }

    fun showInfoMessage(title: String, message: String) {
        val dialog = AlertDialog.Builder(this@ActivityBase, R.style.CustomDialogInfo)
        val vm = ViewModelProvider(this@ActivityBase)[DialogViewModel::class.java]
        val binding: DialogInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@ActivityBase),
            R.layout.dialog_info,
            null,
            false
        )
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@ActivityBase
        }
        dialog.setView(binding.root)
        dialog.setCancelable(false)
        dialog.setPositiveButton(R.string.general_accept) { dial, _ ->
            dial.dismiss()
        }
        vm.setInfo(title, message)
        dialog.create().show()
    }

    fun showImageMessage(path: String) {
        val dialog = AlertDialog.Builder(this@ActivityBase, R.style.CustomDialogInfo)
        val vm = ViewModelProvider(this@ActivityBase)[DialogViewModel::class.java]
        val binding: DialogImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@ActivityBase),
            R.layout.dialog_image,
            null,
            false
        )
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@ActivityBase
        }
        dialog.setView(binding.root)
        dialog.setCancelable(false)
        dialog.setPositiveButton(R.string.general_accept) { dial, _ ->
            dial.dismiss()
        }

        lifecycleScope.launch(Dispatchers.Main) {
            Glide.with(this@ActivityBase)
                .load(path)
                .optionalFitCenter()
                .placeholder(R.drawable.ic_sand_clock)
                .error(R.drawable.ic_no_photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.dialogImage)
        }
        dialog.create().show()
    }

    fun showMovieMessage(knowFor: KnowFor) {
        val dialog = AlertDialog.Builder(this@ActivityBase, R.style.CustomDialogInfo)
        val vm = ViewModelProvider(this@ActivityBase)[DialogViewModel::class.java]
        val binding: DialogMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@ActivityBase),
            R.layout.dialog_movie,
            null,
            false
        )
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@ActivityBase
        }
        dialog.setView(binding.root)
        dialog.setCancelable(false)
        dialog.setPositiveButton(R.string.general_accept) { dial, _ ->
            dial.dismiss()
        }

        with(binding){
            itemActvTitle.text = knowFor.title
            itemAdultName.text = getString(if (knowFor.isAdult) R.string.yes else R.string.not)
            itemOriginalLanguage.text = knowFor.originalLanguage
            itemOriginalTitle.text = knowFor.originalTitle
            itemActvReleaseDate.text = knowFor.releaseDate
            itemActvRating.text = knowFor.voteAverage.toString()
            itemActvVotes.text = knowFor.voteCount.toString()
            itemActvReview.text = knowFor.overview
        }

//        lifecycleScope.launch(Dispatchers.Main) {
//            Glide.with(this@ActivityBase)
//                .load("${BuildConfig.URL_IMGS}${knowFor.backdrop_path}")
//                .optionalFitCenter()
//                .placeholder(R.drawable.ic_sand_clock)
//                .error(R.drawable.ic_no_photo)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .into(binding.aivBackdrop)
//
//            Glide.with(this@ActivityBase)
//                .load("${BuildConfig.URL_IMGS}${knowFor.poster_path}")
//                .optionalFitCenter()
//                .placeholder(R.drawable.ic_sand_clock)
//                .error(R.drawable.ic_no_photo)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .into(binding.aivPoster)
//        }
        dialog.create().show()
    }

    fun <T> launchActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }

    fun <T> launchActivity(clazz: Class<T>, bundle: Bundle) {
        val intent = Intent(this, clazz).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        intent.putExtra(ARG_EXTRAS, bundle)
        startActivity(intent)
    }

    fun showToastMessage(message: String, duration: Int) {
        Toast.makeText(this@ActivityBase, message, duration).show()
    }

    fun showToastMessage(message: String) {
        Toast.makeText(this@ActivityBase, message, Toast.LENGTH_LONG).show()
    }

    abstract fun initUI()

    abstract fun setListeners()

    abstract fun setObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        setListeners()
        setObservers()
    }
}