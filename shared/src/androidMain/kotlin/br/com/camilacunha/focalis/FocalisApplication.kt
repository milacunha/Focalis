package br.com.camilacunha.focalis

import android.app.Application
import br.com.camilacunha.focalis.di.appModule
import org.koin.core.context.startKoin

class FocalisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(appModule) }
    }
}