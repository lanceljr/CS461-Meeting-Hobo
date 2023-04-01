package com.example.project

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project.databinding.ActivityMainBinding
import okhttp3.*
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
        val it = Intent(this, HomeActivity::class.java)
        startActivity(it)
        val client = OkHttpClient()

        val requestBody: RequestBody = FormBody.Builder()
            .add("email", findViewById<EditText>(R.id.editTextEmail).text.toString())
            .add("password", findViewById<EditText>(R.id.editTextPassword).text.toString())
            .build()


        val request = Request.Builder()
            .url("http://127.0.0.1/login")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                createToast("There has been an error. Please try again!")

            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.code == 401) {
                    createToast("Email or password is incorrect. Please try again!")
                } else {
                    val it = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(it)
                }
            }
        })
    }

    private fun createToast(message: String) {
        val toast = Toast.makeText(this@MainActivity,
            message,
            Toast.LENGTH_LONG)
        val toastView = toast.view
        toastView?.setBackgroundColor(Color.RED)
        val text = toastView?.findViewById(android.R.id.message) as TextView
        text.setTextColor(Color.WHITE)
        toast.show()
    }
}