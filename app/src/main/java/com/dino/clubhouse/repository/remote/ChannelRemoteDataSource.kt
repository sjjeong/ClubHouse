package com.dino.clubhouse.repository.remote

import com.dino.clubhouse.remote.model.ChannelListResponse

interface ChannelRemoteDataSource {

    suspend fun getChannelList(): ChannelListResponse

}
