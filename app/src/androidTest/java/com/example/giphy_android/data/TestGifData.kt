package com.example.giphy_android.data

import com.example.giphy_android.data.remote.response.gif.Data
import com.example.giphy_android.data.remote.response.gif.Meta
import com.example.giphy_android.data.remote.response.gif.Pagination
import com.example.giphy_android.data.remote.response.gif.images.Images
import com.example.giphy_android.data.remote.response.gif.images.Original
import com.example.giphy_android.data.remote.response.gif.random.RandomGifResponse

object TestGifData {
    val randomGif = RandomGifResponse(
        data = Data(
            type = "sport",
            id = "123",
            slug = "exciting-sport-action",
            bitlyUrl = "http://bit.ly/sport-gif",
            embedUrl = "https://embed.example.com/sport",
            username = "sportsfanatic",
            source = "sportschannel.com",
            rating = "pg",
            createDateTime = "2024-03-21T12:00:00Z",
            title = "Exciting Sport Action",
            altText = "Exciting Sport Action",
            url = "https://example.com/sport-gif",
            images = Images(Original("https://example.com/sport-gif/original"))
        ),
        pagination = Pagination(
            totalCount = 1,
            count = 1,
            offset = 0
        ),
        meta = Meta(
            status = 200,
            msg = "OK",
            responseId = "random_response_id_123"
        )
    )

    val randomGifSearchResult = listOf(
        RandomGifResponse(
            data = Data(
                type = "music",
                id = "456",
                slug = "groovy-music-vibes",
                bitlyUrl = "http://bit.ly/music-gif",
                embedUrl = "https://embed.example.com/music",
                username = "musiclover",
                source = "musicchannel.com",
                rating = "pg",
                createDateTime = "2024-03-24T08:30:00Z",
                title = "Groovy Music Vibes",
                altText = "Groovy Music Vibes",
                url = "https://example.com/music-gif",
                images = Images(Original("https://example.com/music-gif/original"))
            ),
            pagination = Pagination(
                totalCount = 1,
                count = 1,
                offset = 0
            ),
            meta = Meta(
                status = 200,
                msg = "OK",
                responseId = "random_response_id_456"
            )
        ),
        RandomGifResponse(
            data = Data(
                type = "music",
                id = "789",
                slug = "funky-music-beat",
                bitlyUrl = "http://bit.ly/funky-music-gif",
                embedUrl = "https://embed.example.com/funky-music",
                username = "musicaddict",
                source = "groovychannel.com",
                rating = "pg",
                createDateTime = "2024-03-24T10:45:00Z",
                title = "Funky Music Beat",
                altText = "Funky Music Beat",
                url = "https://example.com/funky-music-gif",
                images = Images(Original("https://example.com/funky-music-gif/original"))
            ),
            pagination = Pagination(
                totalCount = 1,
                count = 1,
                offset = 0
            ),
            meta = Meta(
                status = 200,
                msg = "OK",
                responseId = "random_response_id_789"
            )
        ),
        RandomGifResponse(
            data = Data(
                type = "music",
                id = "101112",
                slug = "rhythmic-dance-party",
                bitlyUrl = "http://bit.ly/dance-party-gif",
                embedUrl = "https://embed.example.com/dance-party",
                username = "dance_enthusiast",
                source = "rhythmchannel.com",
                rating = "pg",
                createDateTime = "2024-03-24T12:15:00Z",
                title = "Rhythmic Dance Party",
                altText = "Rhythmic Dance Party",
                url = "https://example.com/dance-party-gif",
                images = Images(Original("https://example.com/dance-party-gif/original"))
            ),
            pagination = Pagination(
                totalCount = 1,
                count = 1,
                offset = 0
            ),
            meta = Meta(
                status = 200,
                msg = "OK",
                responseId = "random_response_id_101112"
            )
        )
    )
}