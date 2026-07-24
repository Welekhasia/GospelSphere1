package com.example.data.model

import com.example.data.local.PrayerEntity

object SampleGospelData {

    val sampleTracks = listOf(
        GospelTrack(
            id = "track_1",
            title = "Way Maker (Live Worship)",
            artist = "Sinach & GospelSphere Choir",
            album = "Way Maker Live in Lagos",
            durationSeconds = 312,
            genre = "Worship",
            playsCount = "1.8M",
            lyrics = """
                You are here, moving in our midst
                I worship You, I worship You
                You are here, working in this place
                I worship You, I worship You
                
                Way Maker, Miracle Worker, Promise Keeper
                Light in the darkness, my God, that is who You are!
            """.trimIndent()
        ),
        GospelTrack(
            id = "track_2",
            title = "Olorun Agbaye (God of the Universe)",
            artist = "Nathaniel Bassey feat. Chandler Moore",
            album = "Hallelujah Challenge Season 4",
            durationSeconds = 285,
            genre = "Praise",
            playsCount = "950K",
            lyrics = """
                Olorun Agbaye o, You are Mighty!
                There is no place for doubt, You reign supreme.
                King of kings, Lord of lords, We bow before Your throne!
            """.trimIndent()
        ),
        GospelTrack(
            id = "track_3",
            title = "Goodness of God",
            artist = "CeCe Winans",
            album = "Believe For It",
            durationSeconds = 300,
            genre = "Contemporary Gospel",
            playsCount = "2.4M",
            lyrics = """
                I love You, Lord
                For Your mercy never fails me
                All my days, I've been held in Your hands
                From the moment that I wake up
                Until I lay my head
                Oh, I will sing of the goodness of God!
            """.trimIndent()
        ),
        GospelTrack(
            id = "track_4",
            title = "Excess Love",
            artist = "Mercy Chinwo",
            album = "The Cross: My Gaze",
            durationSeconds = 240,
            genre = "Afro Gospel",
            playsCount = "3.1M",
            lyrics = """
                Your love is too much, o
                Jesus, Your love is too much, o
                Too much o, excess love o!
            """.trimIndent()
        ),
        GospelTrack(
            id = "track_5",
            title = "Firm Foundation (He Won't)",
            artist = "Maverick City Music & Cody Carnes",
            album = "Old Church Basement",
            durationSeconds = 340,
            genre = "Praise & Worship",
            playsCount = "1.1M",
            lyrics = """
                Christ is my firm foundation
                The rock on which I stand
                When everything around me is shaken
                I'm never weathered by the storm
            """.trimIndent()
        )
    )

    val sampleVideos = listOf(
        GospelVideo(
            id = "vid_1",
            title = "The Power of Persistent Prayer & Unshakable Faith",
            speaker = "Pastor David O. Oyedepo",
            category = "Sermon",
            duration = "45:12",
            views = "320K",
            publishDate = "2 days ago"
        ),
        GospelVideo(
            id = "vid_2",
            title = "Sunday Praise & Worship Live Service Stream",
            speaker = "Grace Sanctuary Covenant Choir",
            category = "Live Stream",
            duration = "1:20:00",
            views = "14K watching live",
            publishDate = "LIVE NOW",
            isLiveNow = true
        ),
        GospelVideo(
            id = "vid_3",
            title = "How God Answered My Miracle Healing Testimonial",
            speaker = "Evangelist Sarah Jenkins",
            category = "Short",
            duration = "0:58",
            views = "890K",
            publishDate = "1 week ago"
        ),
        GospelVideo(
            id = "vid_4",
            title = "Understanding Spiritual Warfare & Biblical Peace",
            speaker = "Dr. Charles Stanley Legacy",
            category = "Sermon",
            duration = "28:40",
            views = "510K",
            publishDate = "3 days ago"
        ),
        GospelVideo(
            id = "vid_5",
            title = "Global Gospel Youth Night - Praise Experience Premiere",
            speaker = "GospelSphere Youth Ministry",
            category = "Premiere",
            duration = "35:00",
            views = "45K scheduled",
            publishDate = "Tonight at 8 PM"
        )
    )

    val sampleChurches = listOf(
        Church(
            id = "church_1",
            name = "Grace Sanctuary Cathedral",
            denomination = "Non-Denominational / Evangelical",
            location = "Nairobi Central & Online Stream",
            pastorName = "Rev. Dr. Emmanuel Kimani",
            totalMembers = "12,500+",
            rating = 4.9,
            description = "A welcoming community dedicated to preaching the uncompromised Word of God, fervent worship, community outreach, and global church planting.",
            serviceTimes = "Sundays: 8:00 AM, 10:30 AM | Wed Bible Study: 6:00 PM"
        ),
        Church(
            id = "church_2",
            name = "Victory Gospel Fellowship",
            denomination = "Pentecostal Assembly",
            location = "London, UK & Digital Campus",
            pastorName = "Pastor Grace Thorne",
            totalMembers = "5,400+",
            rating = 4.8,
            description = "Empowering families through biblical truth, prayer groups, youth mentorship, and vibrant Sunday praise services.",
            serviceTimes = "Sundays: 10:00 AM GMT | Friday Prayer Night: 7:00 PM"
        ),
        Church(
            id = "church_3",
            name = "Redeemer Covenant Chapel",
            denomination = "Baptist / Reformed Alliance",
            location = "Houston, TX, USA",
            pastorName = "Apostle Michael Vance",
            totalMembers = "8,900+",
            rating = 4.9,
            description = "Anchored in scripture, focused on global missions, theological depth, and loving fellowship for all generations.",
            serviceTimes = "Sundays: 9:00 AM, 11:15 AM | Tue Discipleship: 7:00 PM"
        )
    )

    val sampleArtists = listOf(
        GospelArtist(
            id = "artist_1",
            name = "Sinach",
            location = "Lagos, Nigeria",
            genre = "Contemporary Worship",
            bio = "International worship leader, singer, songwriter, and recording artist behind global worship anthems blessed across over 100 nations."
        ),
        GospelArtist(
            id = "artist_2",
            name = "Nathaniel Bassey",
            location = "Nigeria / Global",
            genre = "Trumpet Praise & Hymns",
            bio = "Gospel minister and trumpeter renowned for the Hallelujah Challenge, fostering global midnight worship and prayer encounters."
        ),
        GospelArtist(
            id = "artist_3",
            name = "CeCe Winans",
            location = "Nashville, TN",
            genre = "Traditional & Modern Gospel",
            bio = "15-time Grammy Award-winning gospel legend inspiring millions with soulful praise and uplifting worship albums."
        )
    )

    val sampleVerses = listOf(
        BibleVerse("John", 3, 16, "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life."),
        BibleVerse("Philippians", 4, 13, "I can do all things through Christ which strengtheneth me."),
        BibleVerse("Jeremiah", 29, 11, "For I know the thoughts that I think toward you, saith the LORD, thoughts of peace, and not of evil, to give you an expected end."),
        BibleVerse("Psalm", 23, 1, "The LORD is my shepherd; I shall not want."),
        BibleVerse("Romans", 8, 28, "And we know that all things work together for good to them that love God, to them who are the called according to his purpose."),
        BibleVerse("Proverbs", 3, 5, "Trust in the LORD with all thine heart; and lean not unto thine own understanding."),
        BibleVerse("Isaiah", 40, 31, "But they that wait upon the LORD shall renew their strength; they shall mount up with wings as eagles; they shall run, and not be weary; and they shall walk, and not faint."),
        BibleVerse("Psalm", 91, 1, "He that dwelleth in the secret place of the most High shall abide under the shadow of the Almighty.")
    )

    val sampleReadingPlans = listOf(
        ReadingPlan("plan_1", "30 Days of Psalms & Peace", "Daily reflections and prayers through the Psalms for inner calm and spiritual renewal.", 30, 5, "Prayer & Peace"),
        ReadingPlan("plan_2", "The Gospel of John Encounter", "A 21-day deep dive into the life, miracles, and divinity of Jesus Christ.", 21, 12, "Gospels"),
        ReadingPlan("plan_3", "Wisdom for Daily Living (Proverbs)", "One chapter of Proverbs each day to align your choices with heavenly wisdom.", 31, 1, "Wisdom"),
        ReadingPlan("plan_4", "Overcoming Anxiety with Faith", "7-day targeted scripture study on God's protection and peace.", 7, 3, "Encouragement")
    )

    val sampleMarketplace = listOf(
        MarketplaceProduct("m1", "GospelSphere Deluxe Leather Holy Bible (KJV/NIV)", "Bible Publishers Intl", "Bibles & Books", 39.99, 4.9, "Genuine leather-bound study Bible with concordance, maps, and wide margins for notes."),
        MarketplaceProduct("m2", "GospelSphere Worship Keyboard 61-Key Synthesizer", "Kingdom Sound Tech", "Church Gear", 249.00, 4.8, "Portable worship synthesizer loaded with acoustic piano, organ pads, and string presets."),
        MarketplaceProduct("m3", "Faith Over Fear Premium Cotton Gospel Hoodie", "Kingdom Threads Co.", "Apparel", 34.50, 4.9, "Comfortable, stylish Christian apparel featuring high-density embroidery."),
        MarketplaceProduct("m4", "Global Worship Conference 2026 VIP Ticket", "GospelSphere Events", "Tickets", 75.00, 5.0, "Access to all keynote sermon sessions, workshops, front-row seating, and digital conference kit."),
        MarketplaceProduct("m5", "Anointed Oil & Communion Cup Set (100 Pack)", "Sanctuary Supplies", "Church Gear", 28.00, 4.7, "Pre-packaged communion cups with pure grape juice and unleavened wafers for church services.")
    )

    val initialPrayerRequests = listOf(
        PrayerEntity(
            requesterName = "Sister Hannah M.",
            title = "Praying for Healing & Full Recovery",
            content = "Please join me in prayer for my mother who is undergoing surgery this week. Believing God for complete healing and peace in Jesus' name!",
            category = "Healing",
            prayedCount = 38
        ),
        PrayerEntity(
            requesterName = "Brother Joshua K.",
            title = "Guidance for Job Interview & Financial Breakthrough",
            content = "Seeking God's open doors for employment opportunities and financial stability to support my young family.",
            category = "Breakthrough",
            prayedCount = 52
        ),
        PrayerEntity(
            requesterName = "Pastor Mark S.",
            title = "Youth Ministry Revival & Spiritual Growth",
            content = "Praying for our church's upcoming youth retreat to spark salvation, deliverance, and deep hunger for scripture among teens.",
            category = "Ministry",
            prayedCount = 29
        )
    )
}
