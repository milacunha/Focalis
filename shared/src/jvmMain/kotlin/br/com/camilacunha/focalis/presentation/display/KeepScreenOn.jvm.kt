package br.com.camilacunha.focalis.presentation.display

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.win32.StdCallLibrary
import com.sun.jna.win32.W32APIOptions

/**
 * Mantém a tela ligada no Desktop.
 *
 * Só Windows por enquanto. Em macOS/Linux vira no-op silencioso
 */
@Composable
actual fun KeepScreenOn(enabled: Boolean) {
    if (!Platform.isWindows()) return

    val controller = remember { WindowsScreenWakeController() }
    DisposableEffect(enabled) {
        controller.setEnabled(enabled)
        onDispose {
            controller.setEnabled(false)
        }
    }
}

/**
 * Wrapper em torno da API SetThreadExecutionState do Windows.
 *
 * ES_CONTINUOUS: a configuração vale até ser substituída.
 * ES_DISPLAY_REQUIRED: mantém o display ligado.
 */
private class WindowsScreenWakeController {

    fun setEnabled(enabled: Boolean) {
        val flags = if (enabled) {
            ES_CONTINUOUS or ES_DISPLAY_REQUIRED
        } else {
            ES_CONTINUOUS
        }
        Kernel32.INSTANCE.SetThreadExecutionState(flags)
    }

    private companion object {
        const val ES_CONTINUOUS: Int = 0x80000000.toInt()
        const val ES_DISPLAY_REQUIRED: Int = 0x00000002
    }
}

/** Interface JNA pra kernel32.dll. */
private interface Kernel32 : StdCallLibrary {
    fun SetThreadExecutionState(esFlags: Int): Int

    companion object {
        val INSTANCE: Kernel32 = Native.load(
            "kernel32",
            Kernel32::class.java,
            W32APIOptions.DEFAULT_OPTIONS,
        )
    }
}