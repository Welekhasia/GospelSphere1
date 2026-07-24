package com.example.data.model

data class GospelTrack(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val durationSeconds: Int,
    val lyrics: String = "",
    val genre: String = "Praise & Worship",
    val playsCount: String = "124K",
    val artworkRes: Int? = null,
    val audioUrl: String = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
)

data class GospelVideo(
    val id: String,
    val title: String,
    val speaker: String,
    val category: String, // "Sermon", "Short", "Live Stream", "Premiere"
    val duration: String,
    val views: String,
    val publishDate: String,
    val isLiveNow: Boolean = false,
    val videoUrl: String = ""
)

data class Church(
    val id: String,
    val name: String,
    val denomination: String,
    val location: String,
    val pastorName: String,
    val totalMembers: String,
    val rating: Double,
    val description: String,
    val serviceTimes: String,
    val liveStreamStatus: String = "Sunday 10:00 AM EST"
)

data class GospelArtist(
    val id: String,
    val name: String,
    val location: String,
    val genre: String,
    val isVerified: Boolean = true,
    val bio: String,
    val topTracksCount: Int = 12,
    val albumTitle: String = "Heaven's Grace"
)

data class BibleVerse(
    val book: String,
    val chapter: Int,
    val verse: Int,
    val text: String
)

data class ReadingPlan(
    val id: String,
    val title: String,
    val description: String,
    val totalDays: Int,
    val currentDay: Int = 1,
    val category: String
)

data class MarketplaceProduct(
    val id: String,
    val name: String,
    val seller: String,
    val category: String, // "Bibles & Books", "Apparel", "Instruments", "Church Gear", "Tickets"
    val priceUsd: Double,
    val rating: Double,
    val description: String
)

data class UserProfile(
    val name: String = "Apostle David Mwangi",
    val email: String = "david.mwangi@gospelsphere.org",
    val role: String = "Ministry Leader & Gospel Artist",
    val churchName: String = "Grace Sanctuary Covenant Church",
    val location: String = "Nairobi / International",
    val isVerified: Boolean = true
)
