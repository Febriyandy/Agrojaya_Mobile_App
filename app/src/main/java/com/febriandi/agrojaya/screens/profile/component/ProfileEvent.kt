package com.febriandi.agrojaya.screens.profile.component

import android.net.Uri

//Profile Event
sealed class ProfileEvent {
    data class UpdateProfile(
        val username: String,
        val phoneNumber: String
    ) : ProfileEvent()

    object RefreshProfilePhoto : ProfileEvent()
    object RefreshUserData : ProfileEvent()
    data class UpdateProfilePhoto(val uri: Uri) : ProfileEvent()
}