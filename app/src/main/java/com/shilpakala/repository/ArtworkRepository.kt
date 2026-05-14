package com.shilpakala.repository

import com.shilpakala.data.local.ArtworkDao
import com.shilpakala.data.local.TimelineDao
import com.shilpakala.data.model.Artwork
import com.shilpakala.data.model.HeritageSection
import com.shilpakala.data.model.TimelineStage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Single source of truth for artworks. Backed by Room DB.
 */
class ArtworkRepository(
    private val artworkDao: ArtworkDao,
    private val timelineDao: TimelineDao
) {

    fun getAllArtworks(): Flow<List<Artwork>> = artworkDao.getAllArtworks()

    suspend fun getArtworkById(id: String): Artwork? = artworkDao.getArtworkById(id)

    fun getArtworksByCategory(category: String): Flow<List<Artwork>> {
        return if (category == "All") artworkDao.getAllArtworks()
        else artworkDao.filterByCategory(category)
    }

    fun searchArtworks(query: String): Flow<List<Artwork>> {
        return if (query.isBlank()) artworkDao.getAllArtworks()
        else artworkDao.searchArtworks(query)
    }

    suspend fun getTimelineForArtwork(artworkId: String): List<TimelineStage> =
        timelineDao.getTimelineForArtwork(artworkId)

    val categories: Flow<List<String>> = artworkDao.getAllArtworks().map { list ->
        listOf("All") + list.map { it.category }.distinct().sorted()
    }

    val heritageSections: List<HeritageSection> = MockData.heritageSections

    fun getArtworksByArtist(userId: Int): Flow<List<Artwork>> =
        artworkDao.getArtworksByArtist(userId)

    suspend fun insertArtwork(artwork: Artwork) = artworkDao.insert(artwork)

    suspend fun updateArtwork(artwork: Artwork) = artworkDao.update(artwork)

    suspend fun deleteArtwork(id: String) = artworkDao.deleteById(id)

    suspend fun insertTimelineStages(stages: List<TimelineStage>) = timelineDao.insertAll(stages)

    suspend fun updateTimelineStages(artworkId: String, stages: List<TimelineStage>) {
        timelineDao.deleteByArtworkId(artworkId)
        val stagesToInsert = stages.map { it.copy(id = 0, artworkId = artworkId) }
        timelineDao.insertAll(stagesToInsert)
    }

    suspend fun deleteTimelineStages(artworkId: String) = timelineDao.deleteByArtworkId(artworkId)

    suspend fun populateDatabaseIfNeeded() {
        if (artworkDao.getCount() == 0) {
            val allArtworks = MockData.artworks
            val allTimelines = MockData.artworks.flatMap { it.timelineStages }

            artworkDao.insertAll(allArtworks)
            timelineDao.insertAll(allTimelines)
        }
    }
}
