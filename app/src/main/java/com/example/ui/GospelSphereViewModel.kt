package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeminiAssistantService
import com.example.data.local.GospelSphereDatabase
import com.example.data.model.GospelTrack
import com.example.data.model.GospelVideo
import com.example.data.model.SampleGospelData
import com.example.data.model.UserProfile
import com.example.data.repository.GospelSphereRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val isUser: Boolean,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isLoading: Boolean = false
)

data class PaymentReceipt(
    val transactionId: String,
    val itemNameOrMinistry: String,
    val amountUsd: Double,
    val paymentMethod: String,
    val timestamp: Long = System.currentTimeMillis()
)

class GospelSphereViewModel(application: Application) : AndroidViewModel(application) {

    private val db = GospelSphereDatabase.getDatabase(application)
    private val repository = GospelSphereRepository(db.gospelSphereDao())

    // --- Search & Filtering ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // --- User Profile ---
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    fun updateUserRole(newRole: String) {
        _userProfile.value = _userProfile.value.copy(role = newRole)
    }

    // --- Music Player State ---
    private val _currentlyPlayingTrack = MutableStateFlow<GospelTrack?>(SampleGospelData.sampleTracks.first())
    val currentlyPlayingTrack: StateFlow<GospelTrack?> = _currentlyPlayingTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackPositionSec = MutableStateFlow(0)
    val playbackPositionSec: StateFlow<Int> = _playbackPositionSec.asStateFlow()

    private var playbackJob: Job? = null

    fun playTrack(track: GospelTrack) {
        _currentlyPlayingTrack.value = track
        _playbackPositionSec.value = 0
        _isPlaying.value = true
        startPlaybackSimulation()
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
        if (_isPlaying.value) {
            startPlaybackSimulation()
        } else {
            playbackJob?.cancel()
        }
    }

    fun seekTo(seconds: Int) {
        _playbackPositionSec.value = seconds
    }

    fun nextTrack() {
        val current = _currentlyPlayingTrack.value ?: return
        val list = SampleGospelData.sampleTracks
        val index = list.indexOfFirst { it.id == current.id }
        if (index != -1 && index < list.size - 1) {
            playTrack(list[index + 1])
        } else if (list.isNotEmpty()) {
            playTrack(list.first())
        }
    }

    fun previousTrack() {
        val current = _currentlyPlayingTrack.value ?: return
        val list = SampleGospelData.sampleTracks
        val index = list.indexOfFirst { it.id == current.id }
        if (index > 0) {
            playTrack(list[index - 1])
        } else if (list.isNotEmpty()) {
            playTrack(list.last())
        }
    }

    private fun startPlaybackSimulation() {
        playbackJob?.cancel()
        playbackJob = viewModelScope.launch {
            while (_isPlaying.value) {
                delay(1000)
                val track = _currentlyPlayingTrack.value
                if (track != null) {
                    val nextPos = _playbackPositionSec.value + 1
                    if (nextPos >= track.durationSeconds) {
                        nextTrack()
                    } else {
                        _playbackPositionSec.value = nextPos
                    }
                }
            }
        }
    }

    // --- Room Database Integrations ---
    val allPrayers = repository.allPrayers
    val allFavorites = repository.allFavorites
    val allBookmarks = repository.allBookmarks

    fun submitPrayerRequest(title: String, requester: String, content: String, category: String) {
        viewModelScope.launch {
            repository.addPrayerRequest(title, requester, content, category)
        }
    }

    fun incrementPrayedCount(prayerId: Int) {
        viewModelScope.launch {
            repository.incrementPrayed(prayerId)
        }
    }

    fun toggleFavorite(mediaId: String, type: String, title: String, subtitle: String, imageUrl: String = "") {
        viewModelScope.launch {
            repository.toggleFavorite(mediaId, type, title, subtitle, imageUrl)
        }
    }

    fun toggleBookmark(book: String, chapter: Int, verse: Int, text: String, note: String = "") {
        viewModelScope.launch {
            repository.toggleBookmark(book, chapter, verse, text, note)
        }
    }

    // --- Giving & Payment Simulator ---
    private val _paymentReceipt = MutableStateFlow<PaymentReceipt?>(null)
    val paymentReceipt: StateFlow<PaymentReceipt?> = _paymentReceipt.asStateFlow()

    fun processGivingOrPurchase(title: String, amountUsd: Double, method: String) {
        viewModelScope.launch {
            delay(1200) // simulate processing
            val txId = "GS-TX-${(100000..999999).random()}"
            _paymentReceipt.value = PaymentReceipt(
                transactionId = txId,
                itemNameOrMinistry = title,
                amountUsd = amountUsd,
                paymentMethod = method
            )
        }
    }

    fun clearReceipt() {
        _paymentReceipt.value = null
    }

    // --- GospelSphere AI Assistant ---
    private val _aiMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                isUser = false,
                text = "Grace and peace be with you! I am GospelSphere AI. How can I assist your spiritual journey today? You can ask me for Bible verse explanations, sermon recommendations, gospel music picks, or church guidance."
            )
        )
    )
    val aiMessages: StateFlow<List<ChatMessage>> = _aiMessages.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    fun sendAiMessage(prompt: String) {
        if (prompt.isBlank() || _isAiThinking.value) return

        val userMsg = ChatMessage(isUser = true, text = prompt)
        _aiMessages.value = _aiMessages.value + userMsg
        _isAiThinking.value = true

        viewModelScope.launch {
            val result = GeminiAssistantService.generateChristianResponse(prompt)
            _isAiThinking.value = false
            result.onSuccess { aiText ->
                _aiMessages.value = _aiMessages.value + ChatMessage(isUser = false, text = aiText)
            }.onFailure { err ->
                val fallbackText = "Blessings! (Note: ${err.localizedMessage ?: "Unable to reach AI"}). Here is a scripture for your day: 'The LORD is my strength and my shield; my heart trusted in him, and I am helped.' - Psalm 28:7"
                _aiMessages.value = _aiMessages.value + ChatMessage(isUser = false, text = fallbackText)
            }
        }
    }
}
