package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class AssignSpeaker : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpeakerAdapter
    private lateinit var dataList: ArrayList<Pair<String, String>>
    private var meetingId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_speaker)

        val it = intent
        Log.i("intent", it.toString())
        var intentData = it.getParcelableExtra<MyDataModel>("data")
        meetingId = intentData?.id.toString()
        Log.i("data", intentData.toString())
        dataList = intentData?.sentences!!
        recyclerView = findViewById(R.id.recyclerSpeaker)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SpeakerAdapter(dataList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    fun saveSpeakers(view: View) {
        val json = JSONObject()

        for (i in 0 until findViewById<RecyclerView>(R.id.recyclerSpeaker).childCount) {
            val viewHolder = findViewById<RecyclerView>(R.id.recyclerSpeaker).getChildViewHolder(
                findViewById<RecyclerView>(R.id.recyclerSpeaker).getChildAt(i))
            var dataFromApi = dataList.get(i)
            if (viewHolder is SpeakerAdapter.MyViewHolder) {
                val editText = viewHolder.itemView.findViewById<EditText>(R.id.editTextSpeaker)
                val text = editText.text.toString()
                json.put(dataFromApi.second.toString(), text)
            }
        }
        val finalJson = JSONObject()
        finalJson.put("data", json)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://10.0.2.2:5000/assignspeakers/" + meetingId)
            .put(finalJson.toString().toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                createToast("There has been an error. Please try again!")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                createToast("Speakers successfully assigned")
                val it = Intent(this@AssignSpeaker, HomeActivity::class.java)
                it.putExtra("goToRecordings", true)
                startActivity(it)
            }
        })

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