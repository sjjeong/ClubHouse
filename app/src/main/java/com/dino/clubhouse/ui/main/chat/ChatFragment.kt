package com.dino.clubhouse.ui.main.chat

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.FragmentChatBinding
import com.dino.clubhouse.ui.main.MainActivity
import com.dino.library.ui.DinoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : DinoFragment<FragmentChatBinding, ChatViewModel>(R.layout.fragment_chat) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel {
            joinEvent eventObserve  {
                (activity as? MainActivity)?.startChat(it)
            }
            leaveEvent eventObserve {
                (activity as? MainActivity)?.leave()
            }
        }
    }

    companion object {

        fun newInstance(channelKey: String) = ChatFragment().apply {
            arguments = bundleOf(ChatViewModel.KEY_CHANNEL_KEY to channelKey)
        }

    }

}
