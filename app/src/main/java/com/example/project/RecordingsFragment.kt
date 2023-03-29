package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecordingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordingsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        return dataList
    }
}