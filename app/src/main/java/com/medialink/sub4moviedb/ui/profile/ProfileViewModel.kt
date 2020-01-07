package com.medialink.sub4moviedb.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medialink.sub4moviedb.model.Profile

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<Profile>().apply {
        val profile = Profile(1, "Bambang Ariyanto",
            125, 333, 218,
            "cyber.hecker@gmail.com", "+62813 4501 9699")
        value = profile
    }
    val text: LiveData<Profile> = _text
}