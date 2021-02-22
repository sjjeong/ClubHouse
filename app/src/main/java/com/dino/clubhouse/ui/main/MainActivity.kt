package com.dino.clubhouse.ui.main

import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityMainBinding
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DinoActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main)
