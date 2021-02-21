package com.dino.clubhouse.repository.remote

import com.dino.clubhouse.remote.model.StartPhoneNumberAuthResponse

interface AuthRemoteDataSource {

    suspend fun startPhoneNumberAuth(phoneNumber: String): StartPhoneNumberAuthResponse

    suspend fun resendPhoneNumberAuth(phoneNumber: String)

    suspend fun completePhoneNumberAuth(phoneNumber: String, verificationCode: String)

}
