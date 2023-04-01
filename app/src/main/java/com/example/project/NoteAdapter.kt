package com.example.project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(var data: List<Note>, val context: FragmentActivity) :
    RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cardTitle = itemView.findViewById<TextView>(R.id.idTVNote)
        private val cardDate = itemView.findViewById<TextView>(R.id.idTVDate)

        fun bind(item: Note) {
            cardTitle.text = item.title
            cardDate.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val item = data[position]
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra("data", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
