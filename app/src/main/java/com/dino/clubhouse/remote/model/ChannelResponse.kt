package com.dino.clubhouse.remote.model


import com.google.gson.annotations.SerializedName

data class ChannelResponse(
    @SerializedName("channel")
    val channel: String,
    @SerializedName("channel_id")
    val channelId: Int,
    @SerializedName("creator_user_profile_id")
    val creatorUserProfileId: Int,
    @SerializedName("feature_flags")
    val featureFlags: List<String>,
    @SerializedName("handraise_permission")
    val handraisePermission: Int,
    @SerializedName("is_club_admin")
    val isClubAdmin: Boolean,
    @SerializedName("is_club_member")
    val isClubMember: Boolean,
    @SerializedName("is_handraise_enabled")
    val isHandraiseEnabled: Boolean,
    @SerializedName("is_private")
    val isPrivate: Boolean,
    @SerializedName("is_social_mode")
    val isSocialMode: Boolean,
    @SerializedName("should_leave")
    val shouldLeave: Boolean,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("topic")
    val topic: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("users")
    val users: List<User>,
    @SerializedName("token")
    val token: String?,
    @SerializedName("pubnub_token")
    val pubnubToken: String?,
    @SerializedName("pubnub_heartbeat_value")
    val pubnubHeartbeatValue: Int?,
    @SerializedName("pubnub_heartbeat_interval")
    val pubnubHeartbeatInterval: Int?,
) {
    data class User(
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("is_followed_by_speaker")
        val isFollowedBySpeaker: Boolean,
        @SerializedName("is_invited_as_speaker")
        val isInvitedAsSpeaker: Boolean,
        @SerializedName("is_moderator")
        val isModerator: Boolean,
        @SerializedName("is_new")
        val isNew: Boolean,
        @SerializedName("is_speaker")
        val isSpeaker: Boolean,
        @SerializedName("name")
        val name: String,
        @SerializedName("photo_url")
        val photoUrl: String,
        @SerializedName("skintone")
        val skintone: Int,
        @SerializedName("time_joined_as_speaker")
        val timeJoinedAsSpeaker: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("username")
        val username: String
    )
}
