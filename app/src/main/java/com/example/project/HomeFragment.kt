package com.example.project

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (requireActivity().intent != null) {
            val sharedPreferences =
                requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            // Retrieve the token from SharedPreferences
            val name = sharedPreferences.getString("name", "")
            requireActivity().findViewById<TextView>(R.id.textView2).text = name
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val myButton = view.findViewById<Button>(R.id.button)
        myButton.setOnClickListener(this::onClick)
        val sharedPreferences =
            requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        // Retrieve the token from SharedPreferences
        val name = sharedPreferences.getString("name", "")
        val textView = view.findViewById<TextView>(R.id.textView2)
        textView.text = name
        return view
    }

    override fun onClick(view: View) {
        val mainActivity = activity as HomeActivity
        mainActivity.goToRecordings()
    }
}