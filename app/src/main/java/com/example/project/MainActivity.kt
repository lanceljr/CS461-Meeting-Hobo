package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToRegister(view: View) {
        val it = Intent(this, RegisterActivity::class.java)
        startActivity(it)
    }

    fun login(view: View) {
        val client = OkHttpClient()

        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()


        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        val json = jsonObject.toString()


        val request = Request.Builder()
            .url("http://10.0.2.2:5000/login")
            .post(json.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                createToast("There has been an error. Please try again!")

            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    if (response.code == 401) {
                        createToast("Email or password is incorrect. Please try again!")
                    } else {
                        val it = Intent(this@MainActivity, HomeActivity::class.java)
                        val body = JSONObject(response.body?.string()!!)
                        println(body)
                        val userid = body.getString("userid")
                        val name = body.getString("name")
                        println(userid)
                        // Initialize the SharedPreferences
                        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

                        // Store the token in SharedPreferences
                        sharedPreferences.edit().putString("userid", userid).apply()
                        sharedPreferences.edit().putString("name", name).apply()
                        response.body?.close()
                        startActivity(it)
                    }
                } catch (e: Exception) {
                    println(e)
                }

            }
        })
    }

    private fun createToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val toast = Toast.makeText(this@MainActivity,
                message,
                Toast.LENGTH_LONG)
            val toastView = toast.view
            toastView?.setBackgroundColor(Color.RED)
            val text = toastView?.findViewById<TextView>(android.R.id.message)
            text?.setTextColor(Color.WHITE)
            toast.show()
        }

    }
}