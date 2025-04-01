package br.com.camilacunha

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val timer: MutableState<String> = mutableStateOf("Vamos começar?")
val timeInSeconds: MutableState<Int> = mutableStateOf(0)
lateinit var job: Job
val timerIsRunning: MutableState<Boolean> = mutableStateOf(false)

fun start() {
    println("start()")
    timerIsRunning.value = true

    val scope = CoroutineScope(Dispatchers.Default)
    job = scope.launch {
        val timerInSeconds = timeInSeconds.value
        var seconds = timerInSeconds

        try {
            repeat(timerInSeconds) {
                println("segundos $seconds")
                showTime(seconds)
                delay(1000)
                seconds -= 1
            }
            timer.value = "Acabou o tempo"
        } finally {
            timerIsRunning.value = false
            println("finally")
        }
        println("start")
    }
}

fun stop() {
    println("stop()")

    CoroutineScope(Dispatchers.Default).launch {
        job.cancelAndJoin()
        timer.value = "Timer cancelado"
    }
    println("stop")
}

fun showTime(totalSeconds: Int) {
    println("showTime()")
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    val formattedMinutes = minutes.toString().padStart(2, '0')
    val formattedSeconds = seconds.toString().padStart(2, '0')

    timer.value = "$formattedMinutes:$formattedSeconds"
    println("showTime")
}

