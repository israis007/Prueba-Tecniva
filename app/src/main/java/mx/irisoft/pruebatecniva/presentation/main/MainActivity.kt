package mx.irisoft.pruebatecniva.presentation.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.databinding.ActivityMainBinding
import mx.irisoft.pruebatecniva.domain.enums.SearchType
import mx.irisoft.pruebatecniva.presentation.base.ActivityBase

@AndroidEntryPoint
class MainActivity : ActivityBase() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
    }
    private lateinit var navController: NavController
    private var lastSearchType = SearchType.TITLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun initUI() {
        val navHost =
            supportFragmentManager.findFragmentById(binding.navHostFragmentActivityMain.id) as NavHostFragment
        navController = navHost.findNavController()
        navController.enableOnBackPressed(false)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_people,
                R.id.navigation_movies,
                R.id.navigation_movies_favorites,
                R.id.navigation_notes,
                R.id.navigation_upload_images
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setupActionBarWithNavController(navController)
        }

        binding.navView.setupWithNavController(navController)

    }

    override fun setListeners() {

    }

    override fun setObservers() {

    }

    fun mostrarDialogoFiltro(showAuthor: Boolean, search: (SearchType) -> Unit) {
        val opciones = if (!showAuthor) arrayOf(
            getString(R.string.filter_option_title),
            getString(R.string.filter_option_content)
        ) else arrayOf(
            getString(R.string.filter_option_title),
            getString(R.string.filter_option_content),
            getString(R.string.filter_option_author)
        )


        MaterialAlertDialogBuilder(this)
            .setTitle("Elige una opción")
            .setSingleChoiceItems(opciones, lastSearchType.value) { dialog, which ->
                lastSearchType = when (which) {
                    0 -> SearchType.TITLE
                    1 -> SearchType.CONTENT
                    else -> SearchType.AUTHOR
                }
                search(lastSearchType)
                dialog.dismiss()
            }
            .setCancelable(false)
            .setNegativeButton("Cancelar", null)
            .show()
    }

    fun resetSearch() {
        lastSearchType = SearchType.AUTHOR
    }
}