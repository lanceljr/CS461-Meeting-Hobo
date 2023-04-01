package com.example.project

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.project.playback.AndroidAudioPlayer
import com.example.project.record.AndroidAudioRecorder
import java.io.File

class RecordActivity : AppCompatActivity() {
    private var recording: Boolean = false
    private var playing: Boolean = false
    private lateinit var timerText: TextView
    private var timer: CountDownTimer? = null
    private var timerRunning: Boolean = false
    private var seconds: Long = 0
    private val recorder by lazy {

        AndroidAudioRecorder(this)
    }

    private val player by lazy {
        AndroidAudioPlayer(this)
    }

    private var audioFile: File? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestPermissions(
            arrayOf(android.Manifest.permission.RECORD_AUDIO), 0
        )
    }

    fun startRecording(view: View) {

        if (!recording) {
            recording = true
            startTimer()
            File(this.cacheDir, "audio.mp3").also {
                recorder.start(it)
                audioFile = it
            }
        } else {
            recording = false
            recorder.stop()
            stopTimer()
        }
    }

    fun playRecording(view: View) {
        if (!playing) {
            playing = true
            player.playFile(audioFile!!)
        } else {
            playing = false
            player.stop()

        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seconds++
                updateTimer()
            }

            override fun onFinish() {}
        }.start()
        timerRunning = true
    }

    private fun stopTimer() {
        timer?.cancel()
        timerRunning = false
        seconds = 0
        updateTimer()
    }

    private fun updateTimer() {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        val timeString = String.format("%02d:%02d:%02d", hours, minutes, secs)
        timerText.text = timeString
    }
}