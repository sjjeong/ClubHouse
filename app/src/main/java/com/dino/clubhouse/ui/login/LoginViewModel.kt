package com.dino.clubhouse.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dino.library.ui.DinoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : DinoViewModel() {

    val phoneNumber = MutableLiveData<String>()

    val code = MutableLiveData<String>()

    private val _isSentCode = MutableLiveData(false)
    val isSentCode: LiveData<Boolean> = _isSentCode

    fun sendCode() {
        val isSentCode = isSentCode.value ?: false
        if (isSentCode) {
            resendPhoneNumberAuth()
        } else {
            startPhoneNumberAuth()
        }
    }

    private fun resendPhoneNumberAuth() {

    }

    private fun startPhoneNumberAuth() {

    }

    fun next() {
        // TODO: 2021/02/22 complete phone number auth
    }


}
