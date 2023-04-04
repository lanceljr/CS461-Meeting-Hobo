package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class NoteAdapter(var data: List<Note>, val context: FragmentActivity) :
    RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cardTitle = itemView.findViewById<TextView>(R.id.noteName)
        private val cardDate = itemView.findViewById<TextView>(R.id.noteDate)

        fun bind(item: Note) {
            cardTitle.text = item.title
            cardDate.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)
        return MyViewHolder(view)
    }

    private fun createToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val toast = Toast.makeText(context,
                message,
                Toast.LENGTH_LONG)
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
            val item = data[position]
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra("data", item)
            context.startActivity(intent)
        }

        holder.itemView.findViewById<ImageView>(R.id.deleteNote).setOnClickListener {
            // TODO: Delete meeting
            val client = OkHttpClient()
            val noteId = data[position].noteId

            val request = Request.Builder()
                .url("http://10.0.2.2:5000/deletenote/" + noteId)
                .delete()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    createToast("There has been an error. Please try again!")
                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    try {
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.putExtra("toNotes", true)
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
