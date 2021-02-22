package com.dino.clubhouse.remote.impl

import com.dino.clubhouse.pref.PrefManager
import com.dino.clubhouse.remote.model.CommonResponse
import com.dino.clubhouse.remote.model.CompletePhoneNumberAuthResponse
import com.dino.clubhouse.remote.network.ClubHouseApi
import com.dino.clubhouse.repository.remote.AuthRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class AuthRemoteDataSourceImpl @Inject constructor(
    private val clubHouseApi: ClubHouseApi,
    private val prefManager: PrefManager,
) :
    AuthRemoteDataSource {
    override suspend fun startPhoneNumberAuth(phoneNumber: String): CommonResponse {
        return clubHouseApi.startPhoneNumberAuth(phoneNumber)
    }

    override suspend fun resendPhoneNumberAuth(phoneNumber: String): CommonResponse {
        return clubHouseApi.resendPhoneNumberAuth(phoneNumber)
    }

    override suspend fun completePhoneNumberAuth(
        phoneNumber: String,
        verificationCode: String,
    ): CompletePhoneNumberAuthResponse {
        return clubHouseApi.completePhoneNumberAuth(phoneNumber, verificationCode).also {
            prefManager.userToken = it.authToken ?: ""
            prefManager.userId = it.userProfile?.userId ?: ""
            prefManager.waitlisted = it.isWaitlisted ?: false
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

}
