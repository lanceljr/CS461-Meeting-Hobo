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
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

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
        val button = view.findViewById(R.id.idFAB) as FloatingActionButton
        button.setOnClickListener{
            val activity = activity as HomeActivity
            activity.goToNoteActivity()
        }
        recyclerView = view.findViewById(R.id.notesRV)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NoteAdapter(getDataFromApi(), requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        // Set the data for the RecyclerView
        val dataList = getDataFromApi()
        adapter.data = dataList
    }

    private fun getDataFromApi(): List<Note> {
        // Call the API and retrieve the data
        // Convert the data into a list of MyData objects
        var data = Note("IDP Meeting", "21", "NOTESIRTHJOWEIHNRFIOWENOFWEF", "1")
        var data1 = Note("BPAS Meeting", "40", "FIOWUEBNFOIWNEOFNWOEF", "2")
        var dataList = ArrayList<Note>()
        dataList.add(data)
        dataList.add(data1)
        // Initialize the SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        // Retrieve the token from SharedPreferences
        val userid = sharedPreferences.getString("userid", "")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://10.0.2.2:5000/getnotes/" + userid)
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
                    val note = d.getString("note")
                    val date = d.getString("date")
                    dataList.add(Note(title, date, note, id))
                }
            }
        })

        if (dataList.size != 0) {
            activity?.findViewById<TextView>(R.id.notesText)?.visibility = View.GONE
        }
        return dataList
    }

    override fun onClick(view: View) {
        val activity = activity as HomeActivity

        activity.goToNoteActivity()
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

//    fun createNewNote(view: View) {
//        val activity = activity as HomeActivity
//        activity.goToNoteActivity()
//    }
}