package com.dino.clubhouse.ui.main

import android.os.Bundle
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityMainBinding
import com.dino.clubhouse.ui.login.LoginActivity
import com.dino.library.ui.DinoActivity
import com.dino.library.ui.DinoViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DinoActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivity.startActivity(this)
    }
}
