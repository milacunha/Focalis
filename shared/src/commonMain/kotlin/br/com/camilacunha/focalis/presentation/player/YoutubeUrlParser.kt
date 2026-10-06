package br.com.camilacunha.focalis.presentation.player

/**
 * Extrai o ID de um vídeo do YouTube a partir de URLs coladas pelo usuário.
 *
 * Formatos aceitos:
 *  - https://www.youtube.com/watch?v=VIDEO_ID
 *  - https://youtu.be/VIDEO_ID
 *  - https://www.youtube.com/embed/VIDEO_ID
 *
 * IDs do YouTube têm exatamente 11 caracteres do alfabeto base64-urlsafe.
 */
internal object YoutubeUrlParser {

    private val VALID_ID = Regex("""^[A-Za-z0-9_-]{11}$""")

    private val TERMINATORS = setOf('&', '?', '#', '/', ' ', '\n', '\t')

    fun extractId(input: String): String? {
        val url = input.trim()
        if (url.isEmpty()) return null
        if (!url.contains("youtube.com") && !url.contains("youtu.be")) return null

        return when {
            "youtu.be/" in url -> extractAfter(url, "youtu.be/")
            "/embed/" in url -> extractAfter(url, "/embed/")
            "v=" in url -> extractAfter(url, "v=")
            else -> null
        }
    }

    fun toEmbedUrl(videoId: String): String =
        "https://www.youtube.com/embed/$videoId"

    fun toWatchUrl(videoId: String): String =
        "https://www.youtube.com/watch?v=$videoId"

    private fun extractAfter(url: String, marker: String): String? {
        val start = url.indexOf(marker) + marker.length
        if (start >= url.length) return null

        var end = start
        while (end < url.length && url[end] !in TERMINATORS) end++

        val candidate = url.substring(start, end)
        return candidate.takeIf { VALID_ID.matches(it) }
    }
}