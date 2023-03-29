package com.example.project.playback

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}