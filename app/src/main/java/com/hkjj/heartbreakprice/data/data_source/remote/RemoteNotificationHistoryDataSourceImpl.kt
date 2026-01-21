package com.hkjj.heartbreakprice.data.data_source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hkjj.heartbreakprice.data.data_source.NotificationHistoryDataSource
import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.model.NotificationType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteNotificationHistoryDataSourceImpl : NotificationHistoryDataSource {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance("heart-break-price")

    override fun getAllNotificationHistories(): Flow<List<Notification>> = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listenerRegistration = firestore.collection("Users")
            .document(uid)
            .collection("Notifications")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val notifications = snapshot.documents.mapNotNull { doc ->
                        val typeStr = doc.getString("type") ?: return@mapNotNull null
                        val type = try {
                            NotificationType.valueOf(typeStr)
                        } catch (e: Exception) {
                            NotificationType.PRICE_DROP // 기본값
                        }

                        Notification(
                            id = doc.id,
                            type = type,
                            productName = doc.getString("productName") ?: "",
                            productImage = doc.getString("productImage") ?: "",
                            message = doc.getString("message") ?: "",
                            timestamp = doc.getDate("timestamp") ?: java.util.Date(),
                            isRead = doc.getBoolean("isRead") ?: false,
                            oldPrice = doc.getLong("oldPrice")?.toInt(),
                            newPrice = doc.getLong("newPrice")?.toInt()
                        )
                    }
                    trySend(notifications)
                }
            }

        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun readAsMarkNotification(id: String) {
        val uid = auth.currentUser?.uid ?: return
        try {
            firestore.collection("Users")
                .document(uid)
                .collection("Notifications")
                .document(id)
                .update("isRead", true)
                .await()
        } catch (e: Exception) {
            // 에러 처리
        }
    }

    override suspend fun deleteNotification(id: String) {
        val uid = auth.currentUser?.uid ?: return
        try {
            firestore.collection("Users")
                .document(uid)
                .collection("Notifications")
                .document(id)
                .delete()
                .await()
        } catch (e: Exception) {
            // 에러 처리
        }
    }
}
