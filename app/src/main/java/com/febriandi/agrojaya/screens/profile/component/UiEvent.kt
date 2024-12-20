package com.febriandi.agrojaya.screens.profile.component

//Ui Event
sealed class UiEvent {
    object Success : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
    data class PhotoUpdateSuccess(val uri: String) : UiEvent()
}