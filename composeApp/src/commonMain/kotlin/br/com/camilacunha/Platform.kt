package br.com.camilacunha

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform