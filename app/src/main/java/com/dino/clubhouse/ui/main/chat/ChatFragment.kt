package com.dino.clubhouse.ui.main.chat

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
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
            joinEvent eventObserve {
                (activity as? MainActivity)?.startChat(it)
            }
            leaveEvent eventObserve {
                (activity as? MainActivity)?.leave()
            }
        }
        setupSpanSizeLookup()
    }

    private fun setupSpanSizeLookup() {
        (binding.rvUser.layoutManager as? GridLayoutManager)?.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val isSpeakersVisible = viewModel.speakersCount != 0
                    val isFollowedBySpeakersVisible = viewModel.followedBySpeakersCount != 0
                    return when (position) {
                        0,
                        viewModel.speakersCount + (if (isSpeakersVisible) 1 else 0),
                        viewModel.speakersCount + (if (isSpeakersVisible) 1 else 0) + viewModel.followedBySpeakersCount + (if (isFollowedBySpeakersVisible) 1 else 0),
                        -> {
                            12
                        }
                        in 1..viewModel.speakersCount -> {
                            4
                        }
                        else -> {
                            3
                        }
                    }
                }
            }

    }

    companion object {

        fun newInstance(channelKey: String) = ChatFragment().apply {
            arguments = bundleOf(ChatViewModel.KEY_CHANNEL_KEY to channelKey)
        }

    }

}
