package com.dino.clubhouse.ui.main

import android.os.Bundle
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityMainBinding
import com.dino.clubhouse.ui.chat.ChatActivity
import com.dino.clubhouse.ui.chat.ChatViewModel
import com.dino.library.ext.startActivity
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DinoActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel {
            showChatEvent eventObserve {
                startActivity<ChatActivity>(ChatViewModel.KEY_CHANNEL to it)
            }
        }
    }

}
