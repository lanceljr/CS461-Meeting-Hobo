package com.example.project

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecordingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordingsFragment : Fragment(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requireActivity().findViewById<Button>(R.id.newRecording).setOnClickListener {
//            goToRecording()
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (requireActivity().intent != null) {
            recyclerView = requireActivity().findViewById(R.id.recycler)
            recyclerView.layoutManager = LinearLayoutManager(context)

            adapter = MyAdapter(getDataFromApi(), requireActivity())
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recordings, container, false)

        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = MyAdapter(getDataFromApi(), requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        val myButton = view.findViewById<Button>(R.id.newRecording)
        myButton.setOnClickListener(this::onClick)

        // Return the fragment view/layout
        return view


    }

//    override fun onResume() {
//        super.onResume()
//
//        // Set the data for the RecyclerView
//        val dataList = getDataFromApi()
//        adapter.data = dataList
//    }

    private fun getDataFromApi(): List<MyDataModel> {
        // Call the API and retrieve the data
        // Convert the data into a list of MyData objects
//        var data = MyDataModel("IDP Meeting", "21", "Iter 2", "2 March 2023", arrayOf(Pair("fjeiwf", 1)).toList())
//        var data1 = MyDataModel("BPAS Meeting", "40", "Workflow Diagram", "10 Feb 2023", arrayOf(Pair("fjeiwf", 1)).toList())
        var dataList = ArrayList<MyDataModel>()
//        dataList.add(data)
//        dataList.add(data1)
        // Initialize the SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        // Retrieve the token from SharedPreferences
        val userid = sharedPreferences.getString("userid", "")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://10.0.2.2:5000/gettranscriptions/" + userid)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                createToast("There has been an error. Please try again!")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = JSONObject(response.body?.string()!!)
                val responseData = body.getJSONArray("data")
                for (i in 0 until responseData.length()) {
                    val d = responseData.getJSONObject(i)
                    val id = d.getString("_id")
                    val title = d.getString("title")
                    val time = d.getString("time")
                    val date = d.getString("date")
                    val hasBeenAssigned = d.getBoolean("hasBeenAssigned")
                    val jsonSentences = d.getJSONArray("sentences")
                    val sentences = ArrayList<Pair<String, String>>()
                    for (i in 0 until jsonSentences.length()) {
                        val sentence = jsonSentences.getJSONArray(i)
                        sentences.add(Pair(sentence.get(1).toString(), sentence.get(0).toString()))
                    }
                    dataList.add(MyDataModel(id, title, time, date, sentences, hasBeenAssigned))
                }
            }
        })
        return dataList

    }

    override fun onClick(view: View) {
        val activity = activity as HomeActivity
        activity.goToRecording()
    }

    private fun createToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val toast = Toast.makeText(requireActivity(),
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