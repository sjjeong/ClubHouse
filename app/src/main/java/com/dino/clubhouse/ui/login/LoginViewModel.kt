package com.dino.clubhouse.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dino.clubhouse.repository.remote.AuthRemoteDataSource
import com.dino.library.ui.DinoViewModel
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
                authRemoteDataSource.completePhoneNumberAuth(phoneNumber,code)
            }
        }
    }


}
