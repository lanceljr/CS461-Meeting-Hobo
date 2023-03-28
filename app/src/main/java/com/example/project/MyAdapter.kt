package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var data: List<MyDataModel>, val context: FragmentActivity) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cardBodyTalkingTime = itemView.findViewById<TextView>(R.id.talkingTime)
        private val cardBodyCommonWord = itemView.findViewById<TextView>(R.id.commonWord)
        private val cardHeaderTitle = itemView.findViewById<TextView>(R.id.meetingName)
        private val cardHeaderTime = itemView.findViewById<TextView>(R.id.headerTalkingTime)
        private val cardHeaderDate = itemView.findViewById<TextView>(R.id.meetingDate)

        fun bind(item: MyDataModel) {
            cardBodyCommonWord.text = item.common
            cardHeaderDate.text = item.date
            cardBodyTalkingTime.text = item.time + "minutes"
            cardHeaderTitle.text = item.title
            cardHeaderTime.text = item.time + "mins"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
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
