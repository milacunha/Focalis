package br.com.camilacunha.focalis.presentation.player

/**
 * Extrai tipo + ID de uma URL do Spotify.
 *
 * Formatos aceitos:
 *  - https://open.spotify.com/track/ID
 *  - https://open.spotify.com/album/ID
 *  - https://open.spotify.com/playlist/ID
 *  - https://open.spotify.com/episode/ID
 *
 * Também aceita variações:
 *  - Com prefixo /intl-XX (ex: /intl-pt/track/ID)
 *  - Com /embed/ antes do tipo
 *  - Com query params (ex: ?si=abc)
 *  - Sem scheme (open.spotify.com/...)
 *
 * IDs do Spotify: 22 caracteres base62.
 */
internal object SpotifyUrlParser {

    private val VALID_TYPES = setOf("track", "album", "playlist", "episode")
    private val VALID_ID = Regex("^[A-Za-z0-9]{22}$")
    data class ContentId(val type: String, val id: String)

    fun extract(input: String): ContentId? {
        val url = input.trim()
        if (url.isEmpty()) return null
        if (!url.contains("open.spotify.com") && !url.contains("spotify.com")) return null

        val segments = url.split("/")
        for (i in segments.indices) {
            val segment = segments[i]
            if (segment !in VALID_TYPES) continue
            if (i + 1 >= segments.size) continue

            val candidate = segments[i + 1]
                .substringBefore("?")
                .substringBefore("#")

            if (VALID_ID.matches(candidate)) {
                return ContentId(type = segment, id = candidate)
            }
        }
        return null
    }

    /** URL de embed (usada por WebView/WKWebView dentro do app). */
    fun toEmbedUrl(content: ContentId): String =
        "https://open.spotify.com/embed/${content.type}/${content.id}"

    /** URL de watch (usada pelo Desktop pra abrir no navegador). */
    fun toWatchUrl(content: ContentId): String =
        "https://open.spotify.com/${content.type}/${content.id}"
}