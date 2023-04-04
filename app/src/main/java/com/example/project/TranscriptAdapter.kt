package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class TranscriptAdapter (var data: ArrayList<Pair<String, String>>, val context: FragmentActivity) :
    RecyclerView.Adapter<TranscriptAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val transcriptNameText = itemView.findViewById<TextView>(R.id.transcriptName)
        private val transcriptSentenceText = itemView.findViewById<TextView>(R.id.transcript_words)

        fun bind(item: Pair<String, String>) {
            transcriptSentenceText.text = item.first
            transcriptNameText.text = item.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transcript_card, parent, false)
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