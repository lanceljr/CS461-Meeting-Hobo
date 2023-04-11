package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color

import android.os.*
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.project.playback.AndroidAudioPlayer
import com.example.project.record.AndroidAudioRecorder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*




class RecordActivity : AppCompatActivity() {
    private var recording: Boolean = false
    private var playing: Boolean = false
    private lateinit var timerText: TextView
    private var timer: CountDownTimer? = null
    private var timerRunning: Boolean = false
    private var seconds: Long = 0
    val bitRate = 1536
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
        setContentView(R.layout.activity_record)
        this.requestPermissions(
            arrayOf(android.Manifest.permission.RECORD_AUDIO), 0
        )
        timerText = findViewById<TextView>(R.id.timeText)


    }

    fun startRecording(view: View) {

        if (!recording) {
            findViewById<ImageView>(R.id.imagePlay).setImageResource(R.drawable.play)
            recording = true
            startTimer()
            File(this.cacheDir, "audio.mp3").also {
                recorder.start(it)
                audioFile = it
            }
        } else {
            recorder.stop()
            stopTimer()
            recording = false
        }
    }


    fun playRecording(view: View) {
        if (!playing) {
            findViewById<ImageView>(R.id.imagePlay).setImageResource(R.drawable.pause)
            playing = true
            player.playFile(audioFile!!)
        } else {
            findViewById<ImageView>(R.id.imagePlay).setImageResource(R.drawable.play)
            playing = false
            player.stop()

        }
    }

    private fun startTimer() {
        seconds = 0
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
        updateTimer()
    }

    private fun updateTimer() {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        val timeString = String.format("%02d:%02d:%02d", hours, minutes, secs)
        timerText.text = timeString
    }

    fun submitFile(view: View) {
        // TODO: call API and submit file
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        // Retrieve the token from SharedPreferences
        val userid = sharedPreferences.getString("userid", "")
//        val mp3File = Mp3File(audioFile!!)

        val name = findViewById<EditText>(R.id.meetingName).text.toString()
        if (name == "") {
            createToast("Please key in a meeting name.")
        } else {
            val client = OkHttpClient()

            // check if recording is completed, throw exception if not

            val audioBytes = audioFile!!.readBytes()
            val audioString = Base64.encodeToString(audioBytes, Base64.DEFAULT)

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", findViewById<EditText>(R.id.meetingName).text.toString())
                .addFormDataPart("recording",audioFile!!.name, RequestBody.create("application/json".toMediaTypeOrNull(), audioString))
                .build()

            val request = Request.Builder()
                .url("http://10.0.2.2:5000/transcribe/" + userid)
                .post(requestBody)
                .build()
            println(request.toString())

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    createToast("There has been an error. Please try again!")
                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    try {
                        createToast("Recording is being processed")
                        goBackToRecordings()
                    }
                    catch (e: Exception) {

                    } finally {
                        response.body?.close()
                    }
                }
            })
        }




    }

    fun backToRecordings(view: View) {
        goBackToRecordings()
    }

    private fun goBackToRecordings() {
        val it = Intent(this, HomeActivity::class.java)
        it.putExtra("goToRecordings", true)
        startActivity(it)
    }

    private fun createToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val toast = Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
            val toastView = toast.view
            toastView?.setBackgroundColor(Color.RED)
            val text = toastView?.findViewById<TextView>(android.R.id.message)
            text?.setTextColor(Color.WHITE)
            toast.show()
        }

    }
}
