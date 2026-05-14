package com.shilpakala.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

// ── User Roles ─────────────────────────────────────────────────────────────────
enum class UserRole {
    BUYER,
    ARTIST,
    GUEST
}

// ── User Entity ────────────────────────────────────────────────────────────────
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val name: String,
    val email: String,
    val passwordHash: String,
    val role: UserRole,
    val whatsappNumber: String = "",
    val profileImageUrl: String = "",
    val bio: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

// ── Artwork Entity ─────────────────────────────────────────────────────────────
/**
 * Represents a single artwork / sculpture in the gallery.
 * Designed for Room database integration.
 */
@Entity(tableName = "artworks")
data class Artwork(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val description: String,
    val material: String,
    val dimensions: String,
    val year: String,
    val imageUrl: String,
    val productId: String,
    val price: String,
    val artistName: String = "Master Shilpi",
    val artistUserId: Int = 0,
    val whatsappNumber: String = "+919876543210",
    val likesCount: Int = 0,
    val savesCount: Int = 0,
    val inquiriesCount: Int = 0,

    // Ignored by Room since it's a one-to-many relationship retrieved separately
    @Ignore val timelineStages: List<TimelineStage> = emptyList()
) {
    // Required empty constructor for Room due to @Ignore and default values
    constructor(
        id: String,
        title: String,
        category: String,
        description: String,
        material: String,
        dimensions: String,
        year: String,
        imageUrl: String,
        productId: String,
        price: String,
        artistName: String,
        artistUserId: Int,
        whatsappNumber: String,
        likesCount: Int,
        savesCount: Int,
        inquiriesCount: Int
    ) : this(
        id, title, category, description, material, dimensions, year,
        imageUrl, productId, price, artistName, artistUserId, whatsappNumber,
        likesCount, savesCount, inquiriesCount, emptyList()
    )
}

// ── Timeline Stage Entity ──────────────────────────────────────────────────────
/**
 * Represents one stage of an artwork's creation journey.
 */
@Entity(
    tableName = "timeline_stages",
    foreignKeys = [
        ForeignKey(
            entity = Artwork::class,
            parentColumns = ["id"],
            childColumns = ["artworkId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimelineStage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val artworkId: String = "",
    val stageTitle: String,
    val stageDescription: String,
    val stageImage: String,
    val durationDays: Int,
    val stageOrder: Int
)

// ── Liked Artwork Entity ───────────────────────────────────────────────────────
@Entity(
    tableName = "liked_artworks",
    primaryKeys = ["userId", "artworkId"],
    foreignKeys = [
        ForeignKey(
            entity = Artwork::class,
            parentColumns = ["id"],
            childColumns = ["artworkId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("artworkId")]
)
data class LikedArtwork(
    val userId: Int,
    val artworkId: String,
    val likedAt: Long = System.currentTimeMillis()
)

// ── Saved/Bookmarked Artwork Entity ────────────────────────────────────────────
@Entity(
    tableName = "saved_artworks",
    primaryKeys = ["userId", "artworkId"],
    foreignKeys = [
        ForeignKey(
            entity = Artwork::class,
            parentColumns = ["id"],
            childColumns = ["artworkId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("artworkId")]
)
data class SavedArtwork(
    val userId: Int,
    val artworkId: String,
    val savedAt: Long = System.currentTimeMillis()
)

// ── Inquiry Entity ─────────────────────────────────────────────────────────────
@Entity(
    tableName = "inquiries",
    foreignKeys = [
        ForeignKey(
            entity = Artwork::class,
            parentColumns = ["id"],
            childColumns = ["artworkId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("artworkId")]
)
data class Inquiry(
    @PrimaryKey(autoGenerate = true) val inquiryId: Int = 0,
    val userId: Int,
    val artworkId: String,
    val artworkTitle: String,
    val productId: String,
    val message: String,
    val sentAt: Long = System.currentTimeMillis(),
    val status: String = "sent" // sent, responded
)

// ── User Activity Entity (Recently Viewed) ─────────────────────────────────────
@Entity(
    tableName = "user_activity",
    foreignKeys = [
        ForeignKey(
            entity = Artwork::class,
            parentColumns = ["id"],
            childColumns = ["artworkId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("artworkId")]
)
data class UserActivity(
    @PrimaryKey(autoGenerate = true) val activityId: Int = 0,
    val userId: Int,
    val artworkId: String,
    val activityType: String, // "viewed", "liked", "saved", "inquired"
    val timestamp: Long = System.currentTimeMillis()
)

// ── Notification Entity ────────────────────────────────────────────────────────
@Entity(tableName = "notifications")
data class AppNotification(
    @PrimaryKey(autoGenerate = true) val notificationId: Int = 0,
    val userId: Int,
    val title: String,
    val message: String,
    val type: String, // "like", "save", "inquiry", "new_artwork", "response"
    val relatedArtworkId: String? = null,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

// ── Heritage Section (non-Room, kept as is) ────────────────────────────────────
/**
 * Heritage article used in the cultural storytelling screen.
 * Aliased as HeritageArticle for PRD alignment.
 */
data class HeritageSection(
    val id: String,
    val category: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val region: String,
    val historicalPeriod: String
)

/** PRD-friendly alias */
typealias HeritageArticle = HeritageSection
