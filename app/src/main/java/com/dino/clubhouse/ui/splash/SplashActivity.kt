package com.dino.clubhouse.ui.splash

import android.os.Bundle
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivitySplashBinding
import com.dino.clubhouse.ui.login.LoginActivity
import com.dino.clubhouse.ui.main.MainActivity
import com.dino.library.ext.startActivity
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :
    DinoActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel {
            showMainEvent eventObserve { showMain() }
            showLoginEvent eventObserve { showLogin() }
        }
    }

    private fun showMain() {
        finish()
        startActivity<MainActivity>()
    }

    private fun showLogin() {
        finish()
        startActivity<LoginActivity>()
    }
}
