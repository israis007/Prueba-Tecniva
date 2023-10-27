package mx.irisoft.pruebatecniva.presentation.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.databinding.ActivitySplashScreenBinding
import mx.irisoft.pruebatecniva.presentation.base.ActivityBase
import mx.irisoft.pruebatecniva.presentation.main.MainActivity

@AndroidEntryPoint
class SplashScreenActivity : ActivityBase() {

    private val vm: SplashScreenViewModel by viewModels()
    private val binding: ActivitySplashScreenBinding by lazy {
        DataBindingUtil.setContentView(this@SplashScreenActivity, R.layout.activity_splash_screen)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun initUI() {
        binding.apply {
            lifecycleOwner = this@SplashScreenActivity
        }
    }

    override fun setListeners() {
        binding.actLavSplash.addAnimatorListener(vm.createAnimationListener())
    }

    override fun setObservers() {
        vm.animationFinished.observe(this) {
            val finished = it ?: return@observe
            if (finished) {
                launchActivity(MainActivity::class.java)
                finish()
            }
        }
    }
}