package com.dino.clubhouse.pref

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import java.util.*
import javax.inject.Inject

class PrefManager @Inject constructor(application: Application) {

    private val sharedPreferences =
        application.getSharedPreferences("session", Context.MODE_PRIVATE)

    val isLogin: Boolean =
        sharedPreferences.getString(KEY_USER_ID, "")
            .isNullOrBlank()
            .not()

    var deviceId: String
        get() = sharedPreferences.getString(
            KEY_DEVICE_ID,
            UUID.randomUUID().toString().toUpperCase()
        ) ?: ""
        set(value) {
            sharedPreferences.edit {
                putString(KEY_DEVICE_ID, value)
            }
        }
    var userId: String
        get() = sharedPreferences.getString(KEY_USER_ID, "") ?: ""
        set(value) {
            sharedPreferences.edit {
                putString(KEY_USER_ID, value)
            }
        }
    var userToken: String
        get() = sharedPreferences.getString(KEY_USER_TOKEN, "") ?: ""
        set(value) {
            sharedPreferences.edit {
                putString(KEY_USER_TOKEN, value)
            }
        }
    var waitlisted: Boolean
        get() = sharedPreferences.getBoolean(KEY_WAITLISTED, false) ?: false
        set(value) {
            sharedPreferences.edit {
                putBoolean(KEY_WAITLISTED, value)
            }
        }

    companion object {
        private const val KEY_DEVICE_ID = "KEY_DEVICE_ID"
        private const val KEY_USER_ID = "KEY_USER_ID"
        private const val KEY_USER_TOKEN = "KEY_USER_TOKEN"
        private const val KEY_WAITLISTED = "KEY_WAITLISTED"
    }

}
