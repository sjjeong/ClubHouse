package com.dino.clubhouse.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dino.clubhouse.pref.PrefManager
import com.dino.library.ui.DinoViewModel
import com.dino.library.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefManager: PrefManager,
) : DinoViewModel() {

    private val _showMainEvent = MutableLiveData<Event<Unit>>()
    val showMainEvent: LiveData<Event<Unit>> = _showMainEvent

    private val _showLoginEvent = MutableLiveData<Event<Unit>>()
    val showLoginEvent: LiveData<Event<Unit>> = _showLoginEvent

    init {
        if (prefManager.isLogin) {
            _showMainEvent.value = Event(Unit)
        } else {
            _showLoginEvent.value = Event(Unit)
        }

    }

}
