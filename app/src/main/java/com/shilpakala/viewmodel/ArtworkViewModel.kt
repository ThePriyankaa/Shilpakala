package com.shilpakala.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.data.local.ShilpaKalaDatabase
import com.shilpakala.data.model.*
import com.shilpakala.repository.ArtworkRepository
import com.shilpakala.repository.EngagementRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Central ViewModel for the Shilpa-Kala gallery.
 * Backed by Room DB via ArtworkRepository.
 */
class ArtworkViewModel(application: Application) : AndroidViewModel(application) {

    private val database = ShilpaKalaDatabase.getDatabase(application)
    private val repository = ArtworkRepository(database.artworkDao(), database.timelineDao())
    val engagementRepository = EngagementRepository(
        database.likedArtworkDao(),
        database.savedArtworkDao(),
        database.inquiryDao(),
        database.userActivityDao(),
        database.notificationDao(),
        database.artworkDao()
    )

    // ── Search & Filter State ────────────────────────────────────────────────
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ── Data Flows ───────────────────────────────────────────────────────────

    val categories: StateFlow<List<String>> = repository.categories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf("All"))

    @OptIn(ExperimentalCoroutinesApi::class)
    val artworks: StateFlow<List<Artwork>> = combine(_searchQuery, _selectedCategory) { query, category ->
        Pair(query, category)
    }.flatMapLatest { (query, category) ->
        // For simplicity with Room, we fetch filtered by category (or all) 
        // and apply search locally, or we could write a complex Room query.
        repository.getArtworksByCategory(category).map { list ->
            if (query.isBlank()) {
                list
            } else {
                list.filter {
                    it.title.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true) ||
                    it.material.contains(query, ignoreCase = true) ||
                    it.artistName.contains(query, ignoreCase = true) ||
                    it.productId.contains(query, ignoreCase = true)
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ── Detail selection ─────────────────────────────────────────────────────
    private val _selectedArtwork = MutableStateFlow<Artwork?>(null)
    val selectedArtwork: StateFlow<Artwork?> = _selectedArtwork.asStateFlow()

    private val _timelineStages = MutableStateFlow<List<TimelineStage>>(emptyList())
    val timelineStages: StateFlow<List<TimelineStage>> = _timelineStages.asStateFlow()

    // ── Heritage ─────────────────────────────────────────────────────────────
    private val _heritageSections = MutableStateFlow<List<HeritageSection>>(emptyList())
    val heritageSections: StateFlow<List<HeritageSection>> = _heritageSections.asStateFlow()

    // ── Artist Artworks ──────────────────────────────────────────────────────
    fun getArtworksByArtist(userId: Int): Flow<List<Artwork>> =
        repository.getArtworksByArtist(userId)

    // ── Engagement Flows ─────────────────────────────────────────────────────
    fun isLiked(userId: Int, artworkId: String): Flow<Boolean> =
        engagementRepository.isLiked(userId, artworkId)

    fun isSaved(userId: Int, artworkId: String): Flow<Boolean> =
        engagementRepository.isSaved(userId, artworkId)

    fun getLikedArtworks(userId: Int): Flow<List<Artwork>> =
        engagementRepository.getLikedArtworks(userId)

    fun getSavedArtworks(userId: Int): Flow<List<Artwork>> =
        engagementRepository.getSavedArtworks(userId)

    fun getRecentlyViewed(userId: Int): Flow<List<Artwork>> =
        engagementRepository.getRecentlyViewed(userId)

    fun getInquiries(userId: Int): Flow<List<Inquiry>> =
        engagementRepository.getInquiriesByUser(userId)

    fun getArtistInquiries(artistUserId: Int): Flow<List<Inquiry>> =
        engagementRepository.getInquiriesForArtist(artistUserId)

    fun getNotifications(userId: Int): Flow<List<AppNotification>> =
        engagementRepository.getNotifications(userId)

    fun getUnreadNotificationCount(userId: Int): Flow<Int> =
        engagementRepository.getUnreadCount(userId)

    // ── Initialization ───────────────────────────────────────────────────────
    init {
        viewModelScope.launch {
            _isLoading.value = true
            repository.populateDatabaseIfNeeded()
            // Artificial delay to show shimmer while DB initializes on first run
            delay(400)
            _heritageSections.value = repository.heritageSections
            _isLoading.value = false
        }
    }

    // ── Actions ──────────────────────────────────────────────────────────────

    fun selectArtwork(artwork: Artwork) {
        _selectedArtwork.value = artwork
        loadTimelineForArtwork(artwork.id)
    }

    fun selectArtworkById(id: String) {
        viewModelScope.launch {
            val artwork = repository.getArtworkById(id)
            _selectedArtwork.value = artwork
            loadTimelineForArtwork(id)
        }
    }

    fun updateSearch(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun loadTimelineForArtwork(artworkId: String) {
        viewModelScope.launch {
            _timelineStages.value = repository.getTimelineForArtwork(artworkId)
        }
    }

    // ── Engagement Actions ───────────────────────────────────────────────────

    fun toggleLike(userId: Int, artworkId: String) {
        viewModelScope.launch {
            engagementRepository.toggleLike(userId, artworkId)
        }
    }

    fun toggleSave(userId: Int, artworkId: String) {
        viewModelScope.launch {
            engagementRepository.toggleSave(userId, artworkId)
        }
    }

    fun recordInquiry(userId: Int, artwork: Artwork) {
        viewModelScope.launch {
            engagementRepository.recordInquiry(userId, artwork)
        }
    }

    fun recordView(userId: Int, artworkId: String) {
        viewModelScope.launch {
            engagementRepository.recordView(userId, artworkId)
        }
    }

    fun markAllNotificationsRead(userId: Int) {
        viewModelScope.launch {
            engagementRepository.markAllNotificationsRead(userId)
        }
    }

    // ── Artist CRUD ──────────────────────────────────────────────────────────

    fun uploadArtwork(artwork: Artwork, timelineStages: List<TimelineStage> = emptyList()) {
        viewModelScope.launch {
            repository.insertArtwork(artwork)
            if (timelineStages.isNotEmpty()) {
                repository.insertTimelineStages(timelineStages)
            }
        }
    }

    fun updateArtwork(artwork: Artwork) {
        viewModelScope.launch {
            repository.updateArtwork(artwork)
        }
    }

    fun updateTimelineStages(artworkId: String, stages: List<TimelineStage>) {
        viewModelScope.launch {
            repository.updateTimelineStages(artworkId, stages)
            // Reload the timeline stages so UI gets the fresh auto-generated IDs
            _timelineStages.value = repository.getTimelineForArtwork(artworkId)
        }
    }

    fun deleteArtwork(artworkId: String) {
        viewModelScope.launch {
            repository.deleteArtwork(artworkId)
        }
    }
}
