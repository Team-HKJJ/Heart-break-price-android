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

        val wishData = hashMapOf(
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

        val productData = hashMapOf(
            "category" to wishProduct.category,
            "image" to wishProduct.image,
            "name" to wishProduct.name,
            "originalPrice" to (wishProduct.originalPrice ?: 0),
            "price" to wishProduct.price,
            "shop" to wishProduct.shop
        )

        firestore.runBatch { batch ->
            // 1. Add to User's Wish list
            val userWishRef = firestore.collection("Users").document(uid)
                .collection("Wishes").document(wishProduct.id)
            batch.set(userWishRef, wishData)

            // 2. Add/Update Product info
            val productRef = firestore.collection("Products").document(wishProduct.id)
            batch.set(productRef, productData)

            // 3. Add to Product's UserList
            val userListRef = productRef.collection("UserList").document(uid)
            batch.set(userListRef, hashMapOf("uid" to uid))
        }.await()
    }

    override suspend fun removeFromWishes(productId: String) {
        val uid = userId ?: return

        val userWishRef = firestore.collection("Users").document(uid)
            .collection("Wishes").document(productId)

        val productRef = firestore.collection("Products").document(productId)
        val userListRef = productRef.collection("UserList").document(uid)

        // 1. Delete from User's Wish list and Product's UserList
        firestore.runBatch { batch ->
            batch.delete(userWishRef)
            batch.delete(userListRef)
        }.await()

        // 2. Check if UserList is empty and delete Product if so
        val userListSnapshot = productRef.collection("UserList").limit(1).get().await()
        if (userListSnapshot.isEmpty) {
            productRef.delete().await()
        }
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
