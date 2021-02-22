package com.dino.clubhouse.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityLoginBinding
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : DinoActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    companion object {

        fun startActivity(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }

    }
}
