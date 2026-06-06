package com.example.uesanapp.data.local

import android.content.Context
import kotlinx.coroutines.flow.Flow

object FavoritesManager {
    private var dao: FavoriteDao? = null

    fun init(context: Context) {
        val db = AppDatabase.getInstance(context)
        dao = db.favoriteDao()
    }

    fun getAllFavorites(): Flow<List<FavoriteCountry>> = dao!!.getAllFavorites()

    suspend fun insert(favorite: FavoriteCountry) = dao!!.insert(favorite)

    suspend fun delete(favorite: FavoriteCountry) = dao!!.delete(favorite)

    suspend fun isFavorite(name: String): Boolean = dao!!.isFavorite(name)
}
