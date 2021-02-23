package com.dino.clubhouse.ui.main.home

import android.os.Bundle
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityHomeBinding
import com.dino.clubhouse.ui.main.chat.ChatActivity
import com.dino.clubhouse.ui.main.chat.ChatViewModel
import com.dino.library.ext.startActivity
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : DinoActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel {
            showChatEvent eventObserve {
                startActivity<ChatActivity>(ChatViewModel.KEY_CHANNEL to it)
            }
        }
    }

}
