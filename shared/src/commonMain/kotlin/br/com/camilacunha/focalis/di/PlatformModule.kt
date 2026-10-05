package br.com.camilacunha.focalis.di

import org.koin.core.module.Module

/**
 * Cada plataforma fornece seu módulo Koin com os bindings específicos
 * (SoundPlayer, futuras coisas). O appModule comum não conhece essas
 * implementações.
 */
expect val platformModule: Module