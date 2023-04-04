package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class SpeakerAdapter (var data: ArrayList<Pair<String, String>>, val context: FragmentActivity) :
    RecyclerView.Adapter<SpeakerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val speakerNameText = itemView.findViewById<TextView>(R.id.speakerName)
        private val speakerSentenceText = itemView.findViewById<TextView>(R.id.speakerSentence)

        fun bind(item: Pair<String, String>) {
            println(itemView)
            println(item.toString())
            println(speakerNameText)
            speakerNameText.setText(item.second.toString())
            speakerNameText.text = item.second.toString()
            speakerSentenceText.text = item.first
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.speaker, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return data.size
    }
}