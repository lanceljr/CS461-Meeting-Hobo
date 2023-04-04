package com.example.project

import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ActivityHomeBinding
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MyAdapter(var data: List<MyDataModel>, val context: FragmentActivity) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cardBodyTalkingTime = itemView.findViewById<TextView>(R.id.talkingTime)
        private val cardHeaderTitle = itemView.findViewById<TextView>(R.id.meetingName)
        private val cardHeaderTime = itemView.findViewById<TextView>(R.id.headerTalkingTime)
        private val cardHeaderDate = itemView.findViewById<TextView>(R.id.meetingDate)

        fun bind(item: MyDataModel) {
            cardHeaderDate.text = item.date
            cardBodyTalkingTime.text = item.time
            cardHeaderTitle.text = item.title
            cardHeaderTime.text = item.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return MyViewHolder(view)
    }

    private fun createToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val toast = Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            )
            val toastView = toast.view
            toastView?.setBackgroundColor(Color.RED)
            val text = toastView?.findViewById<TextView>(android.R.id.message)
            text?.setTextColor(Color.WHITE)
            toast.show()
        }

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            println(item.toString())
            if (!item.hasBeenAssigned) {
                println("Item not assigned")
                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra("data", item)
                intent.putExtra("assignSpeaker", true)
                context.startActivity(intent)
            } else {
                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra("fromRecording", true)
                intent.putExtra("data", item)
                context.startActivity(intent)
            }
        }

        holder.itemView.findViewById<ImageView>(R.id.deleteMeeting).setOnClickListener {

            val client = OkHttpClient()
            val meetingId = data[position].id

            val request = Request.Builder()
                .url("http://10.0.2.2:5000/deletetranscription/" + meetingId)
                .delete()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    createToast("There has been an error. Please try again!")
                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    try {
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.putExtra("goToRecordings", true)
                        response.body?.close()
                        context.startActivity(intent)
                    } catch (e: Exception) {

                    }


                }

            })
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}
