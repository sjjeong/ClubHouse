package com.dino.clubhouse.ui.main.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dino.clubhouse.remote.model.ChannelResponse
import com.dino.clubhouse.repository.remote.ChannelRemoteDataSource
import com.dino.library.ui.DinoViewModel
import com.dino.library.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val channelRemoteDataSource: ChannelRemoteDataSource,
) : DinoViewModel() {

    private val channelKey: String = savedStateHandle[KEY_CHANNEL_KEY] ?: ""

    private val _channel = MutableLiveData<ChannelResponse>()
    val channel: LiveData<ChannelResponse> = _channel

    private val _joinEvent = MutableLiveData<Event<ChannelResponse>>()
    val joinEvent: LiveData<Event<ChannelResponse>> = _joinEvent

    private val _leaveEvent = MutableLiveData<Event<Unit>>()
    val leaveEvent: LiveData<Event<Unit>> = _leaveEvent

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _channel.value = channelRemoteDataSource.getChannel(channelKey)
        }
    }

    fun join() {
        viewModelScope.launch {
            val channel = channelRemoteDataSource.joinChannel(channelKey)
            _joinEvent.value = Event(channel)
        }
    }

    fun leave() {
        viewModelScope.launch {
            channelRemoteDataSource.leaveChannel(channelKey)
            _leaveEvent.value = Event(Unit)
        }
    }

    companion object {
        const val KEY_CHANNEL_KEY = "KEY_CHANNEL_ID"
    }
}
