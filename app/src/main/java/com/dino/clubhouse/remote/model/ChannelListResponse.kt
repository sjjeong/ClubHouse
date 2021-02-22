package com.dino.clubhouse.remote.model


import com.google.gson.annotations.SerializedName

data class ChannelListResponse(
    @SerializedName("channels")
    val channels: List<Channel>,
) {
    data class Channel(
        @SerializedName("channel")
        val channel: String,
        @SerializedName("channel_id")
        val channelId: Int,
        @SerializedName("creator_user_profile_id")
        val creatorUserProfileId: Int,
        @SerializedName("feature_flags")
        val featureFlags: List<String>,
        @SerializedName("has_blocked_speakers")
        val hasBlockedSpeakers: Boolean,
        @SerializedName("is_explore_channel")
        val isExploreChannel: Boolean,
        @SerializedName("is_private")
        val isPrivate: Boolean,
        @SerializedName("is_social_mode")
        val isSocialMode: Boolean,
        @SerializedName("num_all")
        val numAll: Int,
        @SerializedName("num_other")
        val numOther: Int,
        @SerializedName("num_speakers")
        val numSpeakers: Int,
        @SerializedName("topic")
        val topic: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("users")
        val users: List<User>,
    ) {
        data class User(
            @SerializedName("is_followed_by_speaker")
            val isFollowedBySpeaker: Boolean,
            @SerializedName("is_invited_as_speaker")
            val isInvitedAsSpeaker: Boolean,
            @SerializedName("is_moderator")
            val isModerator: Boolean,
            @SerializedName("is_speaker")
            val isSpeaker: Boolean,
            @SerializedName("name")
            val name: String,
            @SerializedName("photo_url")
            val photoUrl: String,
            @SerializedName("time_joined_as_speaker")
            val timeJoinedAsSpeaker: String,
            @SerializedName("user_id")
            val userId: String,
        )
    }
}
