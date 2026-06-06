package com.example.uesanapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_countries ORDER BY ranking ASC")
    fun getAllFavorites(): Flow<List<FavoriteCountry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteCountry)

    @Delete
    suspend fun delete(favorite: FavoriteCountry)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_countries WHERE name = :name)")
    suspend fun isFavorite(name: String): Boolean
}
