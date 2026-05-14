package com.shilpakala.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shilpakala.data.model.*
import kotlinx.coroutines.flow.Flow

// ── Artwork DAO ──────────────────────────────────────────────────────────────────
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artwork: Artwork)

    @Update
    suspend fun update(artwork: Artwork)

    @Query("DELETE FROM artworks WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT COUNT(id) FROM artworks")
    suspend fun getCount(): Int

    @Query("SELECT * FROM artworks WHERE artistUserId = :userId")
    fun getArtworksByArtist(userId: Int): Flow<List<Artwork>>

    @Query("UPDATE artworks SET likesCount = likesCount + 1 WHERE id = :artworkId")
    suspend fun incrementLikes(artworkId: String)

    @Query("UPDATE artworks SET likesCount = CASE WHEN likesCount > 0 THEN likesCount - 1 ELSE 0 END WHERE id = :artworkId")
    suspend fun decrementLikes(artworkId: String)

    @Query("UPDATE artworks SET savesCount = savesCount + 1 WHERE id = :artworkId")
    suspend fun incrementSaves(artworkId: String)

    @Query("UPDATE artworks SET savesCount = CASE WHEN savesCount > 0 THEN savesCount - 1 ELSE 0 END WHERE id = :artworkId")
    suspend fun decrementSaves(artworkId: String)

    @Query("UPDATE artworks SET inquiriesCount = inquiriesCount + 1 WHERE id = :artworkId")
    suspend fun incrementInquiries(artworkId: String)
}

// ── Timeline DAO ─────────────────────────────────────────────────────────────────
@Dao
interface TimelineDao {
    @Query("SELECT * FROM timeline_stages WHERE artworkId = :artworkId ORDER BY stageOrder ASC")
    suspend fun getTimelineForArtwork(artworkId: String): List<TimelineStage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stages: List<TimelineStage>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stage: TimelineStage)

    @Query("DELETE FROM timeline_stages WHERE artworkId = :artworkId")
    suspend fun deleteByArtworkId(artworkId: String)
}

// ── User DAO ─────────────────────────────────────────────────────────────────────
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash LIMIT 1")
    suspend fun login(email: String, passwordHash: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): User?

    @Update
    suspend fun update(user: User)

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun emailExists(email: String): Int
}

// ── Liked Artwork DAO ────────────────────────────────────────────────────────────
@Dao
interface LikedArtworkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(likedArtwork: LikedArtwork)

    @Query("DELETE FROM liked_artworks WHERE userId = :userId AND artworkId = :artworkId")
    suspend fun delete(userId: Int, artworkId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM liked_artworks WHERE userId = :userId AND artworkId = :artworkId)")
    suspend fun isLiked(userId: Int, artworkId: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM liked_artworks WHERE userId = :userId AND artworkId = :artworkId)")
    fun isLikedFlow(userId: Int, artworkId: String): Flow<Boolean>

    @Query("SELECT a.* FROM artworks a INNER JOIN liked_artworks la ON a.id = la.artworkId WHERE la.userId = :userId ORDER BY la.likedAt DESC")
    fun getLikedArtworks(userId: Int): Flow<List<Artwork>>

    @Query("SELECT COUNT(*) FROM liked_artworks WHERE artworkId = :artworkId")
    fun getLikeCount(artworkId: String): Flow<Int>
}

// ── Saved Artwork DAO ────────────────────────────────────────────────────────────
@Dao
interface SavedArtworkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedArtwork: SavedArtwork)

    @Query("DELETE FROM saved_artworks WHERE userId = :userId AND artworkId = :artworkId")
    suspend fun delete(userId: Int, artworkId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_artworks WHERE userId = :userId AND artworkId = :artworkId)")
    suspend fun isSaved(userId: Int, artworkId: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM saved_artworks WHERE userId = :userId AND artworkId = :artworkId)")
    fun isSavedFlow(userId: Int, artworkId: String): Flow<Boolean>

    @Query("SELECT a.* FROM artworks a INNER JOIN saved_artworks sa ON a.id = sa.artworkId WHERE sa.userId = :userId ORDER BY sa.savedAt DESC")
    fun getSavedArtworks(userId: Int): Flow<List<Artwork>>

    @Query("SELECT COUNT(*) FROM saved_artworks WHERE artworkId = :artworkId")
    fun getSaveCount(artworkId: String): Flow<Int>
}

// ── Inquiry DAO ──────────────────────────────────────────────────────────────────
@Dao
interface InquiryDao {
    @Insert
    suspend fun insert(inquiry: Inquiry)

    @Query("SELECT * FROM inquiries WHERE userId = :userId ORDER BY sentAt DESC")
    fun getInquiriesByUser(userId: Int): Flow<List<Inquiry>>

    @Query("SELECT * FROM inquiries WHERE artworkId IN (SELECT id FROM artworks WHERE artistUserId = :artistUserId) ORDER BY sentAt DESC")
    fun getInquiriesForArtist(artistUserId: Int): Flow<List<Inquiry>>

    @Query("SELECT COUNT(*) FROM inquiries WHERE artworkId = :artworkId")
    fun getInquiryCount(artworkId: String): Flow<Int>

    @Update
    suspend fun update(inquiry: Inquiry)
}

// ── User Activity DAO ────────────────────────────────────────────────────────────
@Dao
interface UserActivityDao {
    @Insert
    suspend fun insert(activity: UserActivity)

    @Query("SELECT DISTINCT a.* FROM artworks a INNER JOIN user_activity ua ON a.id = ua.artworkId WHERE ua.userId = :userId AND ua.activityType = 'viewed' ORDER BY ua.timestamp DESC LIMIT :limit")
    fun getRecentlyViewed(userId: Int, limit: Int = 20): Flow<List<Artwork>>

    @Query("SELECT * FROM user_activity WHERE userId = :userId ORDER BY timestamp DESC LIMIT :limit")
    fun getUserActivity(userId: Int, limit: Int = 50): Flow<List<UserActivity>>
}

// ── Notification DAO ─────────────────────────────────────────────────────────────
@Dao
interface NotificationDao {
    @Insert
    suspend fun insert(notification: AppNotification)

    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY createdAt DESC")
    fun getNotifications(userId: Int): Flow<List<AppNotification>>

    @Query("SELECT COUNT(*) FROM notifications WHERE userId = :userId AND isRead = 0")
    fun getUnreadCount(userId: Int): Flow<Int>

    @Query("UPDATE notifications SET isRead = 1 WHERE userId = :userId")
    suspend fun markAllAsRead(userId: Int)

    @Query("UPDATE notifications SET isRead = 1 WHERE notificationId = :notificationId")
    suspend fun markAsRead(notificationId: Int)
}
