package com.example.project

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
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
 * Use the [TranslationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TranslationFragment : Fragment() {
    private var myArg: MyDataModel? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TranscriptAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        arguments?.let {
            myArg = it.getParcelable("data")
        }
        return inflater.inflate(R.layout.fragment_translation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (requireActivity().intent != null) {
            recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recycler_transcript)
            recyclerView.layoutManager = LinearLayoutManager(context)

            adapter = TranscriptAdapter(myArg?.sentences!!, requireActivity())
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.adapter = adapter
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use the argument in the fragment
        Log.i("logging", "LOGGING BEFORE")
//        val args = arguments
        Log.i("logging", myArg.toString())
        if (myArg != null) {
            Log.i("logging", "LOGGING")
            Log.i("logging", myArg?.title.toString())
            requireActivity().findViewById<TextView>(R.id.recordingTitle).text = myArg?.title.toString()
            requireActivity().findViewById<TextView>(R.id.recordingDate).text = myArg?.date.toString()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TranslationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TranslationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}