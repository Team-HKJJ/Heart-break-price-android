package com.hkjj.heartbreakprice.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hkjj.heartbreakprice.domain.model.WishProduct
import com.hkjj.heartbreakprice.domain.repository.WishRepository
import kotlinx.coroutines.tasks.await

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class WishRepositoryImpl : WishRepository {
    private val firestore = FirebaseFirestore.getInstance("heart-break-price")
    private val auth = FirebaseAuth.getInstance()

    private val userId: String?
        get() = auth.currentUser?.uid

    override fun getWishes(): Flow<List<WishProduct>> = callbackFlow {
        val uid = userId
        if (uid == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = firestore.collection("Users")
            .document(uid)
            .collection("Wishes")
            .addSnapshotListener { snapshot, error -> //firestore 데이터 변경 감지
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    val wishes = snapshot.documents.mapNotNull { doc ->
                        WishProduct(
                            id = doc.id,
                            name = doc.getString("name") ?: "",
                            price = doc.getLong("price")?.toInt() ?: 0,
                            originalPrice = doc.getLong("originalPrice")?.toInt(),
                            image = doc.getString("image") ?: "",
                            category = doc.getString("category") ?: "",
                            shop = doc.getString("shop") ?: "",
                            targetPrice = doc.getLong("targetPrice")?.toInt() ?: 0,
                            addedDate = doc.getString("addedDate") ?: "",
                            url = doc.getString("url") ?: ""
                        )
                    }
                    trySend(wishes)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addToWish(wishProduct: WishProduct) {
        val uid = userId ?: throw Exception("로그인이 필요합니다.")
        
        val data = hashMapOf(
            "name" to wishProduct.name,
            "price" to wishProduct.price,
            "originalPrice" to wishProduct.originalPrice,
            "image" to wishProduct.image,
            "category" to wishProduct.category,
            "shop" to wishProduct.shop,
            "targetPrice" to wishProduct.targetPrice,
            "addedDate" to wishProduct.addedDate,
            "url" to wishProduct.url,
            "notify" to true,
            "productRef" to "Products/${wishProduct.id}"
        )

        firestore.collection("Users")
            .document(uid)
            .collection("Wishes")
            .document(wishProduct.id)
            .set(data)
            .await()
    }

    override suspend fun removeFromWishes(productId: String) {
        val uid = userId ?: return
        firestore.collection("Users")
            .document(uid)
            .collection("Wishes")
            .document(productId)
            .delete()
            .await()
    }

    override suspend fun updateTargetPrice(productId: String, targetPrice: Int) {
        val uid = userId ?: return
        firestore.collection("Users")
            .document(uid)
            .collection("Wishes")
            .document(productId)
            .update("targetPrice", targetPrice)
            .await()
    }
}
