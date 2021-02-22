package com.dino.clubhouse.ui.register

import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityRegisterBinding
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity :
    DinoActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register)
