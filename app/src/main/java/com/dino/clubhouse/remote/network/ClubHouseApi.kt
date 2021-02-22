package com.dino.clubhouse.remote.network

import android.app.Application
import android.os.LocaleList
import com.dino.clubhouse.pref.PrefManager
import com.dino.clubhouse.remote.model.ChannelListResponse
import com.dino.clubhouse.remote.model.CompletePhoneNumberAuthResponse
import com.dino.clubhouse.remote.model.CommonResponse
import com.dino.library.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*
import javax.inject.Singleton

interface ClubHouseApi {

    @FormUrlEncoded
    @POST("start_phone_number_auth")
    suspend fun startPhoneNumberAuth(
        @Field("phone_number") phoneNumber: String,
    ): CommonResponse

    @FormUrlEncoded
    @POST("resend_phone_number_auth")
    suspend fun resendPhoneNumberAuth(
        @Field("phone_number") phoneNumber: String,
    ): CommonResponse

    @FormUrlEncoded
    @POST("complete_phone_number_auth")
    suspend fun completePhoneNumberAuth(
        @Field("phone_number") phoneNumber: String,
        @Field("verification_code") verificationCode: String,
    ): CompletePhoneNumberAuthResponse

    @FormUrlEncoded
    @POST("accept_speaker_invite")
    suspend fun acceptSpeakerInvite(
        @Field("channel") channel: String,
        @Field("user_id") userId: Int,
    )

    @FormUrlEncoded
    @POST("active_ping")
    suspend fun activePing(
        @Field("channel") channel: String,
    )

    @FormUrlEncoded
    @POST("audience_reply")
    suspend fun audienceReply(
        @Field("channel") channel: String,
        @Field("raise_hands") raiseHands: Boolean,
        @Field("unraise_hands") unraiseHands: Boolean,
    )

    @GET("check_for_update?is_testflight=0")
    suspend fun checkForUpdate()

    @FormUrlEncoded
    @POST("check_waitlist_status")
    suspend fun checkWaitlistStatus()

    @FormUrlEncoded
    @POST("follow")
    suspend fun follow(
        @Field("user_id") userId: Int,
    )

    @FormUrlEncoded
    @POST("get_channel")
    suspend fun getChannel(
        @Field("channel") channel: String,
    )

    @GET("get_channels")
    suspend fun getChannels() : ChannelListResponse

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

    @FormUrlEncoded
    @POST("get_profile")
    suspend fun getProfile(
        @Field("user_id") userId: Int,
    )

    @FormUrlEncoded
    @POST("join_channel")
    suspend fun joinChannel(
        @Field("channel") channel: String,
        @Field("attribution_source") attributionSource: String = "feed",
        @Field("attribution_details") attributionDetails: String = "eyJpc19leHBsb3JlIjpmYWxzZSwicmFuayI6MX0=",
    )

    @FormUrlEncoded
    @POST("leave_channel")
    suspend fun leaveChannel(
        @Field("channel") channel: String,
    )

    @FormUrlEncoded
    @POST("unfollow")
    suspend fun unfollow(
        @Field("user_id") userId: Int,
    )

    @FormUrlEncoded
    @POST("update_bio")
    suspend fun updateBio(
        @Field("bio") bio: Int,
    )

    @FormUrlEncoded
    @POST("update_name")
    suspend fun updateName(@Field("name") name: String)

    // TODO: 2021/02/21 나중에 사진 업로드 기능 만들때 추가
    @FormUrlEncoded
    @POST("update_photo")
    suspend fun updatePhoto()

    @FormUrlEncoded
    @POST("update_username")
    suspend fun updateUsername(@Field("username") username: String)
}

@Module
@InstallIn(SingletonComponent::class)
class ClubHouseApiModule {

    @Provides
    @Singleton
    fun provideClubHouseApi(retrofit: Retrofit): ClubHouseApi = retrofit.create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.clubhouseapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application, prefManager: PrefManager): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val original = it.request()

                val locales: LocaleList =
                    application.resources.configuration.locales
                val requestBuilder = original
                    .newBuilder()
                    .header("CH-Languages", locales.toLanguageTags())
                    .header("CH-Locale", locales[0].toLanguageTag().replace('-', '_'))
                    .header("Accept", "application/json")
                    .header("CH-AppBuild", API_BUILD_ID)
                    .header("CH-AppVersion", API_BUILD_VERSION)
                    .header("User-Agent", API_UA)
                    .header("CH-DeviceId", prefManager.deviceId)
                if (prefManager.isLogin) {
                    requestBuilder.header("Authorization", "Token " + prefManager.userToken)
                        .header("CH-UserID", prefManager.userId)
                }

                val newRequest = requestBuilder.build()
                it.proceed(newRequest)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()

    companion object {
        private const val API_BUILD_ID = "304"
        private const val API_BUILD_VERSION = "0.1.28"
        private const val API_UA = "clubhouse/$API_BUILD_ID (iPhone; iOS 13.5.1; Scale/3.00)"
    }
}

