package com.dino.clubhouse.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dino.clubhouse.repository.remote.AuthRemoteDataSource
import com.dino.library.ui.DinoViewModel
import com.dino.library.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : DinoViewModel() {

    val phoneNumber = MutableLiveData("")

    val code = MutableLiveData("")

    private val _isSentCode = MutableLiveData(false)
    val isSentCode: LiveData<Boolean> = _isSentCode

    private val _showWaitlistEvent = MutableLiveData<Event<Unit>>()
    val showWaitlistEvent: LiveData<Event<Unit>> = _showWaitlistEvent

    private val _showRegisterEvent = MutableLiveData<Event<Unit>>()
    val showRegisterEvent: LiveData<Event<Unit>> = _showRegisterEvent

    private val _showMainEvent = MutableLiveData<Event<Unit>>()
    val showMainEvent: LiveData<Event<Unit>> = _showMainEvent


    fun sendCode() {
        val isSentCode = isSentCode.value ?: false
        if (isSentCode) {
            resendPhoneNumberAuth()
        } else {
            startPhoneNumberAuth()
        }
    }

    private fun resendPhoneNumberAuth() {
        val phoneNumber = phoneNumber.value ?: return
        if (phoneNumber.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val response = authRemoteDataSource.resendPhoneNumberAuth(phoneNumber)
                    if (response.success) {
                        _isSentCode.value = true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    private fun startPhoneNumberAuth() {
        val phoneNumber = phoneNumber.value ?: return
        if (phoneNumber.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val response = authRemoteDataSource.startPhoneNumberAuth(phoneNumber)
                    if (response.success) {
                        _isSentCode.value = true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun next() {
        val phoneNumber = phoneNumber.value ?: return
        val code = code.value ?: return
        if (
            phoneNumber.isNotBlank()
            && code.isNotBlank()
        ) {
            viewModelScope.launch {
                val data = authRemoteDataSource.completePhoneNumberAuth(phoneNumber, code)
                when {
                    data.isWaitlisted ?: false -> {
                        _showWaitlistEvent.value = Event(Unit)
                    }
                    data.userProfile?.username.isNullOrEmpty() -> {
                        _showRegisterEvent.value = Event(Unit)
                    }
                    else -> {
                        _showMainEvent.value = Event(Unit)
                    }
                }
            }
        }
    }


}
