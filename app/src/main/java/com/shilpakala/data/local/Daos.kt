package com.shilpakala.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shilpakala.data.model.Artwork
import com.shilpakala.data.model.TimelineStage
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao {
    @Query("SELECT * FROM artworks")
    fun getAllArtworks(): Flow<List<Artwork>>

    @Query("SELECT * FROM artworks WHERE id = :id")
    suspend fun getArtworkById(id: String): Artwork?

    @Query("SELECT * FROM artworks WHERE category = :category")
    fun filterByCategory(category: String): Flow<List<Artwork>>

    @Query("SELECT * FROM artworks WHERE title LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%' OR material LIKE '%' || :query || '%' OR artistName LIKE '%' || :query || '%' OR productId LIKE '%' || :query || '%'")
    fun searchArtworks(query: String): Flow<List<Artwork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(artworks: List<Artwork>)

    @Query("SELECT COUNT(id) FROM artworks")
    suspend fun getCount(): Int
}

@Dao
interface TimelineDao {
    @Query("SELECT * FROM timeline_stages WHERE artworkId = :artworkId ORDER BY stageOrder ASC")
    suspend fun getTimelineForArtwork(artworkId: String): List<TimelineStage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stages: List<TimelineStage>)
}
