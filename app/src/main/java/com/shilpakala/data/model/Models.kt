package com.shilpakala.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

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
        artistName: String
    ) : this(
        id, title, category, description, material, dimensions, year, imageUrl, productId, price, artistName, emptyList()
    )
}

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

/**
 * Heritage article used in the cultural storytelling screen.
 * Aliased as HeritageArticle for PRD alignment.
 */
data class HeritageSection(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrl: String
)

/** PRD-friendly alias */
typealias HeritageArticle = HeritageSection
