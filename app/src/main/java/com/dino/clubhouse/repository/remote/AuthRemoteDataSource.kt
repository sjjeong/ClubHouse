package com.dino.clubhouse.repository.remote

import com.dino.clubhouse.remote.model.CommonResponse
import com.dino.clubhouse.remote.model.CompletePhoneNumberAuthResponse

interface AuthRemoteDataSource {

    suspend fun startPhoneNumberAuth(phoneNumber: String): CommonResponse

    suspend fun resendPhoneNumberAuth(phoneNumber: String): CommonResponse

    suspend fun completePhoneNumberAuth(
        phoneNumber: String,
        verificationCode: String,
    ): CompletePhoneNumberAuthResponse

}
