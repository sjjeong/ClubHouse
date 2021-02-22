package com.dino.clubhouse.remote.model


import com.google.gson.annotations.SerializedName

data class CompletePhoneNumberAuthResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("number_of_attempts_remaining")
    val numberOfAttemptsRemaining: Int?,
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("auth_token")
    val authToken: String?,
    @SerializedName("is_onboarding")
    val isOnboarding: Boolean?,
    @SerializedName("is_waitlisted")
    val isWaitlisted: Boolean?,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("user_profile")
    val userProfile: UserProfile?,
) {
    data class UserProfile(
        @SerializedName("name")
        val name: String?,
        @SerializedName("photo_url")
        val photoUrl: String?,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("username")
        val username: String?,
    )
}
