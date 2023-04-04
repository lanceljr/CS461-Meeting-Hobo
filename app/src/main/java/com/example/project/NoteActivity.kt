package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    private var noteIsNew : Boolean = false
    private var title = ""
    private var note = ""
    private var id = "0"


    private fun createToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val toast = Toast.makeText(this@NoteActivity,
                message,
                Toast.LENGTH_LONG)
            val toastView = toast.view
            toastView?.setBackgroundColor(Color.RED)
            val text = toastView?.findViewById<TextView>(android.R.id.message)
            text?.setTextColor(Color.WHITE)
            toast.show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val intent = intent
        if (intent.hasExtra("data")) {
            var data = intent.getParcelableExtra<Note>("data")!!
            if (data != null) {
                title = data.title
                note = data.notes
                id = data.noteId
                val editTextView = findViewById<EditText>(R.id.editNoteName)
                editTextView.setText(data.title)
                findViewById<EditText>(R.id.editNoteDesc).setText(data.notes)
            } else {
                noteIsNew = true
            }
        } else {
            noteIsNew = true
        }




    }

    fun saveNote(view: View) {
        val client = OkHttpClient()
        val jsonObject = JSONObject()

        if (noteIsNew) {
            // Initialize the SharedPreferences
            val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            // Retrieve the token from SharedPreferences
            val userid = sharedPreferences.getString("userid", "")
            title = findViewById<EditText>(R.id.editNoteName).text.toString()
            note = findViewById<EditText>(R.id.editNoteDesc).text.toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())


            jsonObject.put("userid", userid)
            jsonObject.put("title", title)
            jsonObject.put("note", note)
            jsonObject.put("date", currentDate)
            val json = jsonObject.toString()

            val request = Request.Builder()
                .url("http://10.0.2.2:5000/createnote")
                .post(json.toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    createToast("There has been an error. Please try again!")
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val intent = Intent(this@NoteActivity, HomeActivity::class.java)
                        intent.putExtra("toNotes", true)
                        response.body?.close()
                        startActivity(intent)
                    } catch (e: Exception) {
                    }


                }
            })

        } else {
            title = findViewById<EditText>(R.id.editNoteName).text.toString()
            note = findViewById<EditText>(R.id.editNoteDesc).text.toString()
            println(note)

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("Asia/Singapore")
            val currentDate = sdf.parse(Date().toString())
            jsonObject.put("id", id)
            jsonObject.put("title", title)
            jsonObject.put("note", note)
            jsonObject.put("date", currentDate)
            val json = jsonObject.toString()

            val request = Request.Builder()
                .url("http://10.0.2.2:5000/updatenote")
                .put(json.toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    createToast("There has been an error. Please try again!")
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val intent = Intent(this@NoteActivity, HomeActivity::class.java)
                        intent.putExtra("toNotes", true)
                        response.body?.close()
                        startActivity(intent)
                    } catch (e: Exception) {
                    }

                }
            })
        }



    }
}
