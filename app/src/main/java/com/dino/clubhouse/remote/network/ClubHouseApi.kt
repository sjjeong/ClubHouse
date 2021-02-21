package com.dino.clubhouse.remote.network

import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ClubHouseApi {

    @POST("accept_speaker_invite")
    suspend fun acceptSpeakerInvite(
        @Field("channel") channel: String,
        @Field("userId") userId: Int,
    )

    @POST("active_ping")
    suspend fun activePing(
        @Field("channel") channel: String,
    )

    @POST("audience_reply")
    suspend fun audienceReply(
        @Field("channel") channel: String,
        @Field("raiseHands") raiseHands: Boolean,
        @Field("unraiseHands") unraiseHands: Boolean,
    )

    @GET("check_for_update?is_testflight=0")
    suspend fun checkForUpdate()

    @POST("check_waitlist_status")
    suspend fun checkWaitlistStatus()

    @POST("complete_phone_number_auth")
    suspend fun completePhoneNumberAuth(
        @Field("phoneNumber") phoneNumber: String,
        @Field("verificationCode") verificationCode: String,
    )

    @POST("follow")
    suspend fun follow(
        @Field("userId") userId: Int,
    )

    @POST("get_channel")
    suspend fun getChannel(
        @Field("channel") channel: String,
    )

    @GET("get_channels")
    suspend fun getChannels()

    @GET("get_followers")
    suspend fun getFollowers(
        @Query("user_id") userId: Int,
        @Query("page_size") pageSize: Int,
        @Query("page") page: Int,
    )

    @GET("get_following")
    suspend fun getFollowing(
        @Query("user_id") userId: Int,
        @Query("page_size") pageSize: Int,
        @Query("page") page: Int,
    )

    @POST("get_profile")
    suspend fun getProfile(
        @Field("user_id") userId: Int,
    )

    @POST("join_channel")
    suspend fun joinChannel(
        @Field("channel") channel: String,
        @Field("attributionSource") attributionSource: String = "feed",
        @Field("attributionDetails") attributionDetails: String = "eyJpc19leHBsb3JlIjpmYWxzZSwicmFuayI6MX0=",
    )

    @POST("leave_channel")
    suspend fun leaveChannel(
        @Field("channel") channel: String,
    )

    @POST("resend_phone_number_auth")
    suspend fun resendPhoneNumberAuth(
        @Field("phoneNumber") phoneNumber: String,
    )

    @POST("start_phone_number_auth")
    suspend fun startPhoneNumberAuth(
        @Field("phoneNumber") phoneNumber: String,
    )

    @POST("unfollow")
    suspend fun unfollow(
        @Field("user_id") userId: Int,
    )

    @POST("update_bio")
    suspend fun updateBio(
        @Field("bio") bio: Int,
    )

    @POST("update_name")
    suspend fun updateName(@Field("name") name: String)

    // TODO: 2021/02/21 나중에 사진 업로드 기능 만들때 추가
//    @POST("update_photo")
//    suspend fun updatePhoto()

    @POST("update_username")
    suspend fun updateUsername(@Field("username") username: String)
}
