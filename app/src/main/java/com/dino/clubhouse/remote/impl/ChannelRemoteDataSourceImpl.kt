package com.dino.clubhouse.remote.impl

import com.dino.clubhouse.remote.model.ChannelListResponse
import com.dino.clubhouse.remote.network.ClubHouseApi
import com.dino.clubhouse.repository.remote.ChannelRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class ChannelRemoteDataSourceImpl @Inject constructor(private val clubHouseApi: ClubHouseApi) :
    ChannelRemoteDataSource {

    override suspend fun getChannelList(): ChannelListResponse {
        return clubHouseApi.getChannels()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ChannelRemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindChannelRemoteDataSource(channelRemoteDataSourceImpl: ChannelRemoteDataSourceImpl): ChannelRemoteDataSource
}
