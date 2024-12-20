package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.model.NotificationModel
import com.febriandi.agrojaya.utils.Resource
import kotlinx.coroutines.flow.Flow

//Notifikasi Repository
interface NotificationRepository {
    fun getNotifications(uid: String): Flow<Resource<List<NotificationModel>>>
    suspend fun deleteNotification(notificationId: String): Resource<Unit>
    suspend fun deleteAllNotifications(): Resource<Unit>
}