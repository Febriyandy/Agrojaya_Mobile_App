package com.febriandi.agrojaya.screens.notifikasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.NotificationRepository
import com.febriandi.agrojaya.model.NotificationModel
import com.febriandi.agrojaya.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//view model notifikasi
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _notifications = MutableStateFlow<Resource<List<NotificationModel>>>(Resource.Loading)
    val notifications: StateFlow<Resource<List<NotificationModel>>> = _notifications.asStateFlow()

    private val _deleteStatus = MutableStateFlow<Resource<Unit>?>(null)
    val deleteStatus: StateFlow<Resource<Unit>?> = _deleteStatus.asStateFlow()

    fun fetchNotifications() {
        viewModelScope.launch {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                repository.getNotifications(currentUser.uid)
                    .collect { resource ->
                        _notifications.value = resource
                    }
            } else {
                _notifications.value = Resource.Error("Pengguna tidak login")
            }
        }
    }

    fun deleteNotification(notificationId: String) {
        viewModelScope.launch {
            val result = repository.deleteNotification(notificationId)
            _deleteStatus.value = result
        }
    }

    fun deleteAllNotifications() {
        viewModelScope.launch {
            val result = repository.deleteAllNotifications()
            _deleteStatus.value = result
        }
    }

    fun resetDeleteStatus() {
        _deleteStatus.value = null
    }
}