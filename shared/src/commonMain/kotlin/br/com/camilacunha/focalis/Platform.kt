package br.com.camilacunha.focalis

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform