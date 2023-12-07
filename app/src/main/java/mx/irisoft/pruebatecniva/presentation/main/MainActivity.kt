package mx.irisoft.pruebatecniva.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

    val PERMISSIONS = arrayListOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
    )

    private val requestMultiplePermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        // Este mapa contiene los resultados de los permisos
        val isGranted = permissions.entries.all { it.value }
        if (isGranted) {
            this.showToastMessage(getString(R.string.permission_granted))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this@MainActivity.installSplashScreen()

        setContentView(binding.root)
        // Adding more permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            PERMISSIONS.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PERMISSIONS.add(Manifest.permission.READ_MEDIA_IMAGES)
            PERMISSIONS.add(Manifest.permission.POST_NOTIFICATIONS)
        }
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
                R.id.navigation_upload_images,
            ),
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
        val opciones = if (!showAuthor) {
            arrayOf(
                getString(R.string.filter_option_title),
                getString(R.string.filter_option_content),
            )
        } else {
            arrayOf(
                getString(R.string.filter_option_title),
                getString(R.string.filter_option_content),
                getString(R.string.filter_option_author),
            )
        }

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

    fun checkPermissionRequested() {
        if (hasPermission()) {
            this.showToastMessage(getString(R.string.permission_granted))
        } else {
            requestMultiplePermissionsLauncher.launch(PERMISSIONS.toTypedArray())
        }
    }

    private fun hasPermission(): Boolean = PERMISSIONS.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val PERMISSIONS_REQUEST_CODE = 101
    }

    fun checkAndRequestPermissions() {
        val permissionsToRequest = PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        Log.d("Main", "SizepermissionsRequest: ${permissionsToRequest.size}")

        if (permissionsToRequest.isNotEmpty()) {
            permissionsToRequest.forEach {
                Log.d("Main", "Permission Needed: $it")
            }
            ActivityCompat.requestPermissions(this, permissionsToRequest, PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            val allPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (allPermissionsGranted) {
                this.showToastMessage(getString(R.string.permission_granted))
            } else {
                this.showToastMessage(getString(R.string.permission_granted))
            }
        }
    }
}