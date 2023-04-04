package com.example.project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class SpeakerAdapter (var data: List<SpeakerModel>, val context: FragmentActivity) :
    RecyclerView.Adapter<SpeakerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val speakerNameText = itemView.findViewById<TextView>(R.id.speakerName)
        private val speakerSentenceText = itemView.findViewById<TextView>(R.id.speakerSentence)

        fun bind(item: SpeakerModel) {
            speakerNameText.text = item.sentences.second.toString()
            speakerSentenceText.text = item.sentences.first
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_assign_speaker, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

//        holder.itemView.setOnClickListener {
//            val item = data[position]
//            val intent = Intent(context, NoteActivity::class.java)
//            intent.putExtra("data", item)
//            context.startActivity(intent)
//        }
//
//        holder.itemView.findViewById<ImageView>(R.id.deleteNote).setOnClickListener {
//            // TODO: Delete meeting
//        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}