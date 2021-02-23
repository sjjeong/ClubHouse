package com.dino.clubhouse.ui.main.chat

import androidx.lifecycle.*
import com.dino.clubhouse.model.ChatUserInfoPresentation
import com.dino.clubhouse.model.TitlePresentation
import com.dino.clubhouse.remote.model.ChannelResponse
import com.dino.clubhouse.repository.remote.ChannelRemoteDataSource
import com.dino.clubhouse.voice.VoiceService
import com.dino.library.dinorecyclerview.ItemViewType
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

    var speakersCount = 0
    var followedBySpeakersCount = 0
    var othersCount = 0

    val userList: LiveData<List<ItemViewType>> = channel.map { channel ->
        val speakers = channel.users
            .filter { it.isSpeaker }
            .map { ChatUserInfoPresentation(it) }
            .also { speakersCount = it.size }

        val followedBySpeakers = channel.users
            .filter { !it.isSpeaker && it.isFollowedBySpeaker }
            .map { ChatUserInfoPresentation(it) }
            .also { followedBySpeakersCount = it.size }

        val others = channel.users
            .filterNot { it.isSpeaker || it.isFollowedBySpeaker }
            .map { ChatUserInfoPresentation(it) }
            .also { othersCount = it.size }

        mutableListOf<ItemViewType>().apply {
            if (speakers.isNotEmpty()) {
                add(TitlePresentation("Speakers"))
                addAll(speakers)
            }
            if (followedBySpeakers.isNotEmpty()) {
                add(TitlePresentation("Followed by the speakers"))
                addAll(followedBySpeakers)
            }
            if (others.isNotEmpty()) {
                add(TitlePresentation("Others in the room"))
                addAll(others)
            }
        }
    }

    private val _isJoined = MutableLiveData(VoiceService.channel?.channel == channelKey)
    val isJoined: LiveData<Boolean> = _isJoined

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

    fun joinAndLeave() {
        val isJoined = isJoined.value ?: false
        if (isJoined) {
            leave()
        } else {
            join()
        }
    }

    private fun join() {
        viewModelScope.launch {
            val channel = channelRemoteDataSource.joinChannel(channelKey)
            _joinEvent.value = Event(channel)
            _isJoined.value = true
        }
    }

    private fun leave() {
        viewModelScope.launch {
            channelRemoteDataSource.leaveChannel(channelKey)
            _leaveEvent.value = Event(Unit)
            _isJoined.value = false
        }
    }

    companion object {
        const val KEY_CHANNEL_KEY = "KEY_CHANNEL_ID"
    }
}
