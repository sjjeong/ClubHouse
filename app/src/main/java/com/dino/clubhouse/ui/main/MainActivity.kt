package com.dino.clubhouse.ui.main

import android.content.Intent
import android.os.Build
import androidx.fragment.app.commit
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.ActivityMainBinding
import com.dino.clubhouse.remote.model.ChannelResponse
import com.dino.clubhouse.ui.main.chat.ChatFragment
import com.dino.clubhouse.voice.VoiceService
import com.dino.library.ui.DinoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DinoActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    fun showChat() {
//        startActivity<ChatFragment>(ChatViewModel.KEY_CHANNEL to it)
    }

    fun startChat(newChannel: ChannelResponse) {
        if (VoiceService.channel == null
            || VoiceService.channel?.channel != newChannel.channel
        ) {
            VoiceService.channel = newChannel
            val intent = Intent(this, VoiceService::class.java)
            intent.putExtra("channel", newChannel.channel)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

}
