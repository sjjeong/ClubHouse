package com.dino.clubhouse.remote.model


import com.google.gson.annotations.SerializedName

data class StartPhoneNumberAuthResponse(
    @SerializedName("error_message")
    val errorMessage: String?,
    @SerializedName("is_blocked")
    val isBlocked: Boolean?,
    @SerializedName("success")
    val success: Boolean
)
