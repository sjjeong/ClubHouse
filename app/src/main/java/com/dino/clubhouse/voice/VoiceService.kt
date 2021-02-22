package com.dino.clubhouse.voice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.dino.clubhouse.pref.PrefManager
import com.dino.clubhouse.remote.model.ChannelResponse
import com.google.gson.JsonObject
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.objects_api.channel.PNChannelMetadataResult
import com.pubnub.api.models.consumer.objects_api.membership.PNMembershipResult
import com.pubnub.api.models.consumer.objects_api.uuid.PNUUIDMetadataResult
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import com.pubnub.api.models.consumer.pubsub.PNSignalResult
import com.pubnub.api.models.consumer.pubsub.files.PNFileEventResult
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult
import dagger.hilt.android.AndroidEntryPoint
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import javax.inject.Inject

@AndroidEntryPoint
class VoiceService : Service() {

    private var engine: RtcEngine? = null
    private var pubnub: PubNub? = null

    @Inject
    lateinit var prefManager: PrefManager

    private var isSelfModerator: Boolean = false
    private var isSelfSpeaker: Boolean = false

    private val listeners: List<ChannelEventListener> = mutableListOf()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        engine =
            RtcEngine.create(baseContext, KEY_AGORA, object : IRtcEngineEventHandler() {}).apply {
                setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
                setDefaultAudioRoutetoSpeakerphone(true)
                enableAudioVolumeIndication(500, 3, false)
                muteLocalAudioStream(true)
            }
        instance = this
    }

    override fun onDestroy() {
        RtcEngine.destroy()
        instance = null
        engine = null
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (engine != null) {
            updateChannel()
            val nm = getSystemService(NotificationManager::class.java)
            val n: Notification.Builder = Notification.Builder(this)
//                .setSmallIcon(R.drawable.ic_phone_in_talk)
//                .setContentTitle(getString(R.string.ongoing_call))
                .setContentText(intent.getStringExtra("topic"))
//                .setContentIntent(PendingIntent.getActivity(this,
//                    1,
//                    Intent(this, MainActivity::class.java).putExtra("openCurrentChannel", true),
//                    PendingIntent.FLAG_UPDATE_CURRENT))
            if (Build.VERSION.SDK_INT >= 26) {
                if (nm.getNotificationChannel("ongoing") == null) {
                    val nc = NotificationChannel("ongoing",
                        "Ongoing calls",
                        NotificationManager.IMPORTANCE_LOW)
                    nm.createNotificationChannel(nc)
                }
                n.setChannelId("ongoing")
            }
            startForeground(10, n.build())
            doJoinChannel()
        }
        return START_NOT_STICKY
    }

    private fun updateChannel() {
        isSelfModerator = false
        isSelfSpeaker = false
        val userId = prefManager.userId
        channel?.users?.find { it.userId == userId }?.let {
            isSelfModerator = it.isModerator
            isSelfSpeaker = it.isSpeaker
        }
    }

    private fun doJoinChannel() {
        val channel = channel ?: return
        engine!!.joinChannel(channel.token, channel.channel, "", prefManager.userId.toInt())
//        uiHandler.postDelayed(pinger, 30000)
        listeners.forEach { it.onChannelUpdated(channel) }

        val pnConf = PNConfiguration()
        pnConf.subscribeKey = PUBNUB_SUB_KEY
        pnConf.publishKey = PUBNUB_PUB_KEY
        //pnConf.setUuid(UUID.randomUUID().toString());
        pnConf.origin = "clubhouse.pubnub.com"
        pnConf.uuid = prefManager.userId
        pnConf.setPresenceTimeoutWithCustomInterval(channel.pubnubHeartbeatValue!!,
            channel.pubnubHeartbeatInterval!!)
        pnConf.authKey = channel.pubnubToken
        pubnub = PubNub(pnConf)
        pubnub?.addListener(object : SubscribeCallback() {
            override fun status(pubnub: PubNub, pnStatus: PNStatus) {
                Log.d(TAG, "status() called with: pubnub = [$pubnub], pnStatus = [$pnStatus]")
            }

            override fun message(pubnub: PubNub, pnMessageResult: PNMessageResult) {
                Log.d(TAG,
                    "message() called with: pubnub = [$pubnub], pnMessageResult = [$pnMessageResult]")
                val msg = pnMessageResult.message.asJsonObject
                when (msg["action"].asString) {
                    "invite_speaker" -> onInvitedAsSpeaker(msg)
                    "join_channel" -> onUserJoined(msg)
                    "leave_channel" -> onUserLeft(msg)
                }
            }

            override fun presence(pubnub: PubNub, pnPresenceEventResult: PNPresenceEventResult) {
                Log.d(TAG,
                    "presence() called with: pubnub = [$pubnub], pnPresenceEventResult = [$pnPresenceEventResult]")
            }

            override fun signal(pubnub: PubNub, pnSignalResult: PNSignalResult) {
                Log.d(TAG,
                    "signal() called with: pubnub = [$pubnub], pnSignalResult = [$pnSignalResult]")
            }

            override fun uuid(pubnub: PubNub, pnUUIDMetadataResult: PNUUIDMetadataResult) {}
            override fun channel(pubnub: PubNub, pnChannelMetadataResult: PNChannelMetadataResult) {
                Log.d(TAG,
                    "channel() called with: pubnub = [$pubnub], pnChannelMetadataResult = [$pnChannelMetadataResult]")
            }

            override fun membership(pubnub: PubNub, pnMembershipResult: PNMembershipResult) {
                Log.d(TAG,
                    "membership() called with: pubnub = [$pubnub], pnMembershipResult = [$pnMembershipResult]")
            }

            override fun messageAction(
                pubnub: PubNub,
                pnMessageActionResult: PNMessageActionResult,
            ) {
                Log.d(TAG,
                    "messageAction() called with: pubnub = [$pubnub], pnMessageActionResult = [$pnMessageActionResult]")
            }

            override fun file(pubnub: PubNub, pnFileEventResult: PNFileEventResult) {}
        })

        pubnub?.subscribe()?.channels(listOf(
            "users." + prefManager.userId,
            "channel_user." + channel.channel + "." + prefManager.userId,
            //				"channel_speakers."+channel.channel,
            "channel_all." + channel.channel
        ))?.execute()
    }

    private fun onInvitedAsSpeaker(msg: JsonObject) {
//        val ch = msg["channel"].asString
//        if (ch != channel!!.channel) return
//        uiHandler.post(Runnable {
//            for (l in VoiceService.listeners) l.onCanSpeak(msg["from_name"].asString,
//                msg["from_user_id"].asInt)
//        })
    }

    private fun onUserJoined(msg: JsonObject) {
//        val ch = msg["channel"].asString
//        if (ch != channel!!.channel) return
//        val profile = msg.getAsJsonObject("user_profile")
//        val user: ChannelUser = ClubhouseAPIController.getInstance().getGson().fromJson(profile,
//            ChannelUser::class.java)
//        uiHandler.post(Runnable {
//            channel!!.users.add(user)
//            for (l in VoiceService.listeners) l.onUserJoined(user)
//        })
    }

    private fun onUserLeft(msg: JsonObject) {
//        val ch = msg["channel"].asString
//        if (ch != channel!!.channel) return
//        val id = msg["user_id"].asInt
//        uiHandler.post(Runnable {
//            for (user in channel!!.users) {
//                if (user.userId === id) {
//                    channel!!.users.remove(user)
//                    break
//                }
//            }
//            for (l in VoiceService.listeners) l.onUserLeft(id)
//        })
    }

    interface ChannelEventListener {
        fun onUserMuteChanged(id: Int, muted: Boolean)
        fun onUserJoined(user: ChannelResponse.User?)
        fun onUserLeft(id: Int)
        fun onCanSpeak(inviterName: String?, inviterID: Int)
        fun onChannelUpdated(channel: ChannelResponse?)
        fun onSpeakingUsersChanged(ids: List<Int?>?)
    }

    companion object {
        private const val TAG = "dino_log"
        private const val KEY_AGORA = "938de3e8055e42b281bb8c6f69c21f78"
        private const val PUBNUB_PUB_KEY = "pub-c-6878d382-5ae6-4494-9099-f930f938868b"
        private const val PUBNUB_SUB_KEY = "sub-c-a4abea84-9ca3-11ea-8e71-f2b83ac9263d"
        var instance: VoiceService? = null
        var channel: ChannelResponse? = null
    }
}
