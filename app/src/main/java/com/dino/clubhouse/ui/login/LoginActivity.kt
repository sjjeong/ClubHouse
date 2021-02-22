package com.dino.clubhouse.ui.login

import android.os.Bundle
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityLoginBinding
import com.dino.clubhouse.ui.register.RegisterActivity
import com.dino.clubhouse.ui.waitlist.WaitlistActivity
import com.dino.library.ext.startActivity
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint
import com.dino.clubhouse.ui.main.MainActivity as MainActivity1

@AndroidEntryPoint
class LoginActivity : DinoActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel {
            showWaitlistEvent eventObserve { showWaitlist() }
            showRegisterEvent eventObserve { showRegister() }
            showMainEvent eventObserve { showMain() }
        }
    }

    private fun showWaitlist() {
        finish()
        startActivity<WaitlistActivity>()
    }

    private fun showRegister() {
        finish()
        startActivity<RegisterActivity>()
    }

    private fun showMain() {
        finish()
        startActivity<MainActivity1>()
    }

}
