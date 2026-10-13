package br.com.camilacunha.focalis.presentation.player

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SpotifyUrlParserTest {

    // ID real do Spotify (22 chars base62)
    private val trackId = "4cOdK2wGLETKBW3PvgPWqT"
    private val playlistId = "37i9dQZF1DXcBWIGoYBM5M"

    // ---------- Formatos válidos ----------

    @Test
    fun extractsTrack() {
        val result = SpotifyUrlParser.extract("https://open.spotify.com/track/$trackId")
        assertEquals(SpotifyUrlParser.ContentId("track", trackId), result)
    }

    @Test
    fun extractsAlbum() {
        val result = SpotifyUrlParser.extract("https://open.spotify.com/album/$trackId")
        assertEquals(SpotifyUrlParser.ContentId("album", trackId), result)
    }

    @Test
    fun extractsPlaylist() {
        val result = SpotifyUrlParser.extract("https://open.spotify.com/playlist/$playlistId")
        assertEquals(SpotifyUrlParser.ContentId("playlist", playlistId), result)
    }

    @Test
    fun extractsEpisode() {
        val result = SpotifyUrlParser.extract("https://open.spotify.com/episode/$trackId")
        assertEquals(SpotifyUrlParser.ContentId("episode", trackId), result)
    }

    @Test
    fun extractsWithIntlPrefix() {
        val result = SpotifyUrlParser.extract("https://open.spotify.com/intl-pt/track/$trackId")
        assertEquals(SpotifyUrlParser.ContentId("track", trackId), result)
    }

    @Test
    fun extractsFromEmbedUrl() {
        val result = SpotifyUrlParser.extract("https://open.spotify.com/embed/track/$trackId")
        assertEquals(SpotifyUrlParser.ContentId("track", trackId), result)
    }

    @Test
    fun extractsWithShareQueryParam() {
        val result = SpotifyUrlParser.extract("https://open.spotify.com/track/$trackId?si=abc123")
        assertEquals(SpotifyUrlParser.ContentId("track", trackId), result)
    }

    @Test
    fun extractsWithoutScheme() {
        val result = SpotifyUrlParser.extract("open.spotify.com/track/$trackId")
        assertEquals(SpotifyUrlParser.ContentId("track", trackId), result)
    }

    @Test
    fun handlesLeadingAndTrailingWhitespace() {
        val result = SpotifyUrlParser.extract("  https://open.spotify.com/track/$trackId  ")
        assertEquals(SpotifyUrlParser.ContentId("track", trackId), result)
    }

    // ---------- Inválidos ----------

    @Test
    fun returnsNullForEmpty() {
        assertNull(SpotifyUrlParser.extract(""))
    }

    @Test
    fun returnsNullForWhitespaceOnly() {
        assertNull(SpotifyUrlParser.extract("   "))
    }

    @Test
    fun returnsNullForNonSpotifyUrl() {
        assertNull(SpotifyUrlParser.extract("https://example.com/track/$trackId"))
    }

    @Test
    fun returnsNullForArtistUrl() {
        // Artist não é embedável — rejeitamos mesmo sendo URL válida do Spotify.
        assertNull(SpotifyUrlParser.extract("https://open.spotify.com/artist/$trackId"))
    }

    @Test
    fun returnsNullForTooShortId() {
        assertNull(SpotifyUrlParser.extract("https://open.spotify.com/track/abc"))
    }

    @Test
    fun returnsNullForTooLongId() {
        assertNull(SpotifyUrlParser.extract("https://open.spotify.com/track/${trackId}extra"))
    }

    @Test
    fun returnsNullForInvalidIdCharacters() {
        // "_" e "-" não são base62.
        assertNull(SpotifyUrlParser.extract("https://open.spotify.com/track/____________-_________"))
    }

    @Test
    fun returnsNullForUrlWithoutId() {
        assertNull(SpotifyUrlParser.extract("https://open.spotify.com/track/"))
    }

    @Test
    fun returnsNullForUrlWithoutType() {
        assertNull(SpotifyUrlParser.extract("https://open.spotify.com/$trackId"))
    }

    // ---------- Geração de URLs ----------

    @Test
    fun toEmbedUrlBuildsCorrectUrl() {
        assertEquals(
            "https://open.spotify.com/embed/track/$trackId",
            SpotifyUrlParser.toEmbedUrl(SpotifyUrlParser.ContentId("track", trackId)),
        )
    }

    @Test
    fun toWatchUrlBuildsCorrectUrl() {
        assertEquals(
            "https://open.spotify.com/track/$trackId",
            SpotifyUrlParser.toWatchUrl(SpotifyUrlParser.ContentId("track", trackId)),
        )
    }
}