package com.example.project

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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

    override fun onResume() {
        super.onResume()

        // Set the data for the RecyclerView
        val dataList = getDataFromApi()
        adapter.data = dataList
    }

    private fun getDataFromApi(): List<MyDataModel> {
        // Call the API and retrieve the data
        // Convert the data into a list of MyData objects
        var data = MyDataModel("IDP Meeting", "21", "Iter 2", "2 March 2023")
        var data1 = MyDataModel("BPAS Meeting", "40", "Workflow Diagram", "10 Feb 2023")
        var dataList = ArrayList<MyDataModel>()
        dataList.add(data)
        dataList.add(data1)
//        val client = OkHttpClient()
//
//        val request = Request.Builder()
//            .url("https://api.example.com/data")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: okhttp3.Call, e: IOException) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onResponse(call: okhttp3.Call, response: Response) {
//                TODO("Not yet implemented")
//            }
//        })
        if (dataList.size != 0) {
            activity?.findViewById<TextView>(R.id.recordingText)?.visibility = View.GONE
        }
        return dataList
    }

    override fun onClick(view: View) {
        val activity = activity as HomeActivity
        activity.goToRecording()
    }
}