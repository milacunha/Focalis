package br.com.camilacunha

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val showTimeInScreen: MutableState<String> = mutableStateOf("5")
lateinit var job: Job
val timerIsRunning: MutableState<Boolean> = mutableStateOf(false)

fun start() {
    println("start()")
    timerIsRunning.value = true
    showTimeInScreen.value = "5"

    val scope = CoroutineScope(Dispatchers.Default)
    job = scope.launch {
        val timerInSeconds = showTimeInScreen.value.toInt()
        var seconds = timerInSeconds

        try {
            repeat(timerInSeconds) {
                println("segundos $seconds")
                showTime(seconds)
                delay(1000)
                seconds -= 1
            }
            showTimeInScreen.value = "00:00"
            println("Acabou o tempo")
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
        showTimeInScreen.value = "00:00"
        println("Timer cancelado")
    }
    println("stop")
}

fun showTime(totalSeconds: Int) {
    println("showTime()")
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    val formattedMinutes = minutes.toString().padStart(2, '0')
    val formattedSeconds = seconds.toString().padStart(2, '0')

    showTimeInScreen.value = "$formattedMinutes:$formattedSeconds"
    println("showTime")
}

