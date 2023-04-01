package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment(), View.OnClickListener  {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        recyclerView = view.findViewById(R.id.notesRV)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NoteAdapter(getDataFromApi(), requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        return view
    }

    private fun getDataFromApi(): List<Note> {
        // Call the API and retrieve the data
        // Convert the data into a list of MyData objects
        var data = Note("IDP Meeting", "21", "NOTESIRTHJOWEIHNRFIOWENOFWEF", "1")
        var data1 = Note("BPAS Meeting", "40", "FIOWUEBNFOIWNEOFNWOEF", "2")
        var dataList = ArrayList<Note>()
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
            activity?.findViewById<TextView>(R.id.notesText)?.visibility = View.GONE
        }
        return dataList
    }

    override fun onClick(view: View) {
        val activity = activity as HomeActivity
        activity.goToNoteActivity()
    }
}