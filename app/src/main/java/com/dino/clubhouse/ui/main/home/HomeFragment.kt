package com.dino.clubhouse.ui.main.home

import android.os.Bundle
import android.view.View
import com.dino.clubhouse.R
import com.dino.clubhouse.databinding.FragmentHomeBinding
import com.dino.clubhouse.ui.main.MainActivity
import com.dino.library.ui.DinoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : DinoFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel {
            showChatEvent eventObserve { (activity as? MainActivity)?.showChat(it) }
        }
    }

}
