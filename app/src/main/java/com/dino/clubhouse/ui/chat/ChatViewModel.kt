package com.dino.clubhouse.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dino.clubhouse.remote.model.ChannelResponse
import com.dino.clubhouse.repository.remote.ChannelRemoteDataSource
import com.dino.library.ui.DinoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val channelRemoteDataSource: ChannelRemoteDataSource,
) : DinoViewModel() {

    private val channelKey: String = savedStateHandle[KEY_CHANNEL] ?: ""

    private val _channel = MutableLiveData<ChannelResponse>()
    val channel: LiveData<ChannelResponse> = _channel

    init {
        join()
    }

    private fun join() {
        viewModelScope.launch {
            _channel.value = channelRemoteDataSource.joinChannel(channelKey)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _channel.value = channelRemoteDataSource.getChannel(channelKey)
        }
    }

    fun leave() {
        viewModelScope.launch {
            channelRemoteDataSource.leaveChannel(channelKey)

        }
    }

    companion object {
        const val KEY_CHANNEL = "KEY_CHANNEL_ID"
    }
}
