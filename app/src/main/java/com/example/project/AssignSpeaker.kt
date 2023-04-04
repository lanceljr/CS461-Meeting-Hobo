package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class AssignSpeaker : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpeakerAdapter
    private lateinit var dataList: ArrayList<Pair<String, Int>>
    private var meetingId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_speaker)
        findViewById<RecyclerView>(R.id.recyclerSpeaker)
        val it = Intent()
        meetingId = it.getStringExtra("meetingId").toString()
        var intentData = it.getParcelableExtra<MyDataModel>("data")
        dataList = intentData?.sentences as ArrayList<Pair<String, Int>>
        recyclerView = findViewById(R.id.recyclerSpeaker)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SpeakerAdapter(getDataFromApi(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    private fun getDataFromApi(): List<SpeakerModel> {
        // Call the API and retrieve the data
        // Convert the data into a list of MyData objects
        var data = Pair("Sentence 1", 1)
        var data1 = Pair("Sentence 2", 2)
        dataList.add(data)
        dataList.add(data1)
        // Initialize the SharedPreferences
//        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
//        // Retrieve the token from SharedPreferences
//        val userid = sharedPreferences.getString("userid", "")
//
//        val client = OkHttpClient()
//
//        val request = Request.Builder()
//            .url("http://10.0.2.2:5000/getnotes/" + userid)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: okhttp3.Call, e: IOException) {
//                createToast("There has been an error. Please try again!")
//            }
//
//            override fun onResponse(call: okhttp3.Call, response: Response) {
//                val body = JSONObject(response.body?.string()!!)
//                val responseData = body.getJSONArray("data")
//                for (i in 0 until responseData.length()) {
//                    val d = responseData.getJSONObject(i)
//                    val id = d.getString("_id")
//                    val title = d.getString("title")
//                    val note = d.getString("note")
//                    val date = d.getString("date")
//                    dataList.add(Note(title, date, note, id))
//                }
//            }
//        })

        return dataList as List<SpeakerModel>
    }

    fun saveSpeakers(view: View) {
        val json = JSONArray()

        for (i in 0 until findViewById<RecyclerView>(R.id.recyclerSpeaker).childCount) {
            val viewHolder = findViewById<RecyclerView>(R.id.recyclerSpeaker).getChildViewHolder(
                findViewById<RecyclerView>(R.id.recyclerSpeaker).getChildAt(i))
            var dataFromApi = dataList.get(i)
            if (viewHolder is SpeakerAdapter.MyViewHolder) {
                val editText = viewHolder.itemView.findViewById<EditText>(R.id.editTextSpeaker)
                val text = editText.text.toString()
                var jsonObject = JSONObject()
                jsonObject.put("speakerName", text)
                jsonObject.put("initialSpeakerId", dataFromApi.second)
                json.put(jsonObject)
            }
        }
        val finalJson = json.toString()

        //        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
//        // Retrieve the token from SharedPreferences
//        val userid = sharedPreferences.getString("userid", "")
//
//        val client = OkHttpClient()
//
//        val request = Request.Builder()
//            .url("http://10.0.2.2:5000/getnotes/" + userid)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: okhttp3.Call, e: IOException) {
//                createToast("There has been an error. Please try again!")
//            }
//
//            override fun onResponse(call: okhttp3.Call, response: Response) {
//                val body = JSONObject(response.body?.string()!!)
//                val responseData = body.getJSONArray("data")
//                for (i in 0 until responseData.length()) {
//                    val d = responseData.getJSONObject(i)
//                    val id = d.getString("_id")
//                    val title = d.getString("title")
//                    val note = d.getString("note")
//                    val date = d.getString("date")
//                    dataList.add(Note(title, date, note, id))
//                }
//            }
//        })

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