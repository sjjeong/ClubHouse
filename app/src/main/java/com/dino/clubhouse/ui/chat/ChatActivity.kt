package com.dino.clubhouse.ui.chat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityChatBinding
import com.dino.clubhouse.remote.model.ChannelResponse
import com.dino.clubhouse.voice.VoiceService
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : DinoActivity<ActivityChatBinding, ChatViewModel>(R.layout.activity_chat) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel {
            channel observe { startChat(it) }
        }
    }

    private fun startChat(it: ChannelResponse) {
        if (VoiceService.channel == null) {
            VoiceService.channel = it
            val intent = Intent(this@ChatActivity, VoiceService::class.java)
            intent.putExtra("channel", it.channel)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

}
