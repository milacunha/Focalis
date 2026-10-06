package br.com.camilacunha.focalis.presentation.player

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class YoutubeUrlParserTest {

    @Test
    fun extractsIdFromWatchUrl() {
        assertEquals(
            "dQw4w9WgXcQ",
            YoutubeUrlParser.extractId("https://www.youtube.com/watch?v=dQw4w9WgXcQ"),
        )
    }

    @Test
    fun extractsIdFromShortUrl() {
        assertEquals(
            "dQw4w9WgXcQ",
            YoutubeUrlParser.extractId("https://youtu.be/dQw4w9WgXcQ"),
        )
    }

    @Test
    fun extractsIdFromEmbedUrl() {
        assertEquals(
            "dQw4w9WgXcQ",
            YoutubeUrlParser.extractId("https://www.youtube.com/embed/dQw4w9WgXcQ"),
        )
    }

    @Test
    fun extractsIdWithExtraQueryParams() {
        assertEquals(
            "dQw4w9WgXcQ",
            YoutubeUrlParser.extractId("https://www.youtube.com/watch?v=dQw4w9WgXcQ&t=30s"),
        )
    }

    @Test
    fun extractsIdFromShortUrlWithShareParam() {
        assertEquals(
            "dQw4w9WgXcQ",
            YoutubeUrlParser.extractId("https://youtu.be/dQw4w9WgXcQ?si=abc123"),
        )
    }

    @Test
    fun extractsIdFromUrlWithoutScheme() {
        assertEquals(
            "dQw4w9WgXcQ",
            YoutubeUrlParser.extractId("youtube.com/watch?v=dQw4w9WgXcQ"),
        )
    }

    @Test
    fun extractsIdFromMobileUrl() {
        assertEquals(
            "dQw4w9WgXcQ",
            YoutubeUrlParser.extractId("https://m.youtube.com/watch?v=dQw4w9WgXcQ"),
        )
    }

    @Test
    fun handlesLeadingAndTrailingWhitespace() {
        assertEquals(
            "dQw4w9WgXcQ",
            YoutubeUrlParser.extractId("  https://youtu.be/dQw4w9WgXcQ  "),
        )
    }

    // ---------- Inputs inválidos ----------

    @Test
    fun returnsNullForEmptyInput() {
        assertNull(YoutubeUrlParser.extractId(""))
    }

    @Test
    fun returnsNullForWhitespaceOnly() {
        assertNull(YoutubeUrlParser.extractId("   "))
    }

    @Test
    fun returnsNullForNonYoutubeUrl() {
        assertNull(YoutubeUrlParser.extractId("https://example.com/watch?v=dQw4w9WgXcQ"))
    }

    @Test
    fun returnsNullForTooShortId() {
        assertNull(YoutubeUrlParser.extractId("https://youtu.be/short"))
    }

    @Test
    fun returnsNullForTooLongId() {
        assertNull(YoutubeUrlParser.extractId("https://youtu.be/dQw4w9WgXcQextra"))
    }

    @Test
    fun returnsNullForUrlWithoutVideoId() {
        assertNull(YoutubeUrlParser.extractId("https://www.youtube.com/"))
    }

    @Test
    fun returnsNullForWatchUrlWithoutVParam() {
        assertNull(YoutubeUrlParser.extractId("https://www.youtube.com/watch"))
    }
}