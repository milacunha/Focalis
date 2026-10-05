package br.com.camilacunha.focalis.audio

import android.media.AudioAttributes
import android.media.SoundPool
import br.com.camilacunha.focalis.presentation.audio.SoundPlayer
import br.com.camilacunha.focalis.resources.Res

/**
 * Toca o som de fim de timer via SoundPool.
 *
 * SoundPool é ótimo pra sons curtos e repetidos: carrega uma vez na memória
 * e toca com latência mínima. O carregamento é assíncrono — só sabemos que
 * está pronto quando o OnLoadCompleteListener dispara.
 *
 * Estratégia: no init, disparamos o load. Se play() for chamado antes do
 * load terminar, guardamos a intenção e tocamos assim que estiver pronto.
 * Na prática o load leva <100ms e o timer dura minutos, então esse caso
 * quase nunca acontece — mas é o tipo de coisa que falha no device do usuário
 * e nunca no emulador, então tratamos.
 */
internal class AndroidSoundPlayer : SoundPlayer {

    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private var soundId: Int = 0
    private var loaded: Boolean = false
    private var pendingPlay: Boolean = false

    init {
        soundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                loaded = true
                if (pendingPlay) {
                    pendingPlay = false
                    playLoaded()
                }
            }
        }
        // load() é suspenso pela própria lib do Compose Resources, mas
        // SoundPool.load() aceita FileDescriptor. Precisamos ler os bytes primeiro.
        // Fazemos isso de forma bloqueante no init porque o player é criado
        // uma vez só (single no Koin) e o arquivo é pequeno (~57KB).
        loadSound()
    }

    private fun loadSound() {
        // Res.readBytes é suspend, então não dá pra chamar direto do init.
        // Solução: usar runBlocking uma vez no construtor. Aceitável porque
        // o player é um singleton e o construtor roda uma vez.
        val bytes = kotlinx.coroutines.runBlocking {
            Res.readBytes("files/beep.wav")
        }
        // SoundPool não carrega de ByteArray diretamente. Escrevemos num
        // arquivo temporário no cache interno e passamos o path.
        // (Alternativa: AssetManager + res/raw — mas quebra o requisito
        // de ter o arquivo em commonMain/composeResources.)
        val cacheFile = java.io.File.createTempFile("beep", ".wav")
        cacheFile.deleteOnExit()
        cacheFile.writeBytes(bytes)
        soundId = soundPool.load(cacheFile.absolutePath, 1)
    }

    override fun play() {
        if (loaded) {
            playLoaded()
        } else {
            pendingPlay = true
        }
    }

    private fun playLoaded() {
        soundPool.play(
            soundId,
            1f,      // volume esquerdo
            1f,      // volume direito
            1,       // prioridade
            0,       // loop (0 = uma vez só)
            1f,      // rate (1f = velocidade normal)
        )
    }
}