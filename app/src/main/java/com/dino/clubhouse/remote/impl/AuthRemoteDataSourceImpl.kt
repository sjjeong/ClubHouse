package com.dino.clubhouse.remote.impl

import com.dino.clubhouse.remote.model.StartPhoneNumberAuthResponse
import com.dino.clubhouse.remote.network.ClubHouseApi
import com.dino.clubhouse.repository.remote.AuthRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class AuthRemoteDataSourceImpl @Inject constructor(private val clubHouseApi: ClubHouseApi) :
    AuthRemoteDataSource {
    override suspend fun startPhoneNumberAuth(phoneNumber: String): StartPhoneNumberAuthResponse {
        return clubHouseApi.startPhoneNumberAuth(phoneNumber)
    }

    override suspend fun resendPhoneNumberAuth(phoneNumber: String) {
        clubHouseApi.resendPhoneNumberAuth(phoneNumber)
    }

    override suspend fun completePhoneNumberAuth(phoneNumber: String, verificationCode: String) {
        clubHouseApi.completePhoneNumberAuth(phoneNumber, verificationCode)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

}
