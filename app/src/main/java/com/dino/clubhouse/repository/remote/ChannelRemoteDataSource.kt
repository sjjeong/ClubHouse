package com.dino.clubhouse.repository.remote

import com.dino.clubhouse.remote.model.ChannelListResponse
import com.dino.clubhouse.remote.model.ChannelResponse

interface ChannelRemoteDataSource {

    suspend fun getChannelList(): ChannelListResponse

    suspend fun getChannel(channelKey: String): ChannelResponse

    suspend fun joinChannel(channelKey: String): ChannelResponse

    suspend fun leaveChannel(channelKey: String)

}
