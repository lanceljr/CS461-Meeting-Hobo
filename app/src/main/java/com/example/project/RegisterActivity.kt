package com.example.project

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

    }

    fun register(view: View) {

        if (findViewById<EditText>(R.id.editTextPasswordRegister).text.toString() !=
            findViewById<EditText>(R.id.editTextConfirmPassword).text.toString()) {
            createToast("Please ensure your confirmed password is the same as your password!")

        } else {
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            val email = findViewById<EditText>(R.id.editTextEmailRegister).text.toString()
            val password = findViewById<EditText>(R.id.editTextPasswordRegister).text.toString()
            val client = OkHttpClient()

            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("email", email)
            jsonObject.put("password", password)
            val json = jsonObject.toString()


            val request = Request.Builder()
                .url("http://10.0.2.2:5000/register")
                .post(json.toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            println(request.toString())

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e)
                    createToast("There has been an error. Please try again!")

                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        if (response.code == 400) {
                            createToast("User has already been created with this email. Please use another one!")
                        } else {
                            val it = Intent(this@RegisterActivity, MainActivity::class.java)
                            response.body?.close()
                            startActivity(it)
                        }
                    } catch (e: Exception) {
                    }

                }
            })
        }

    }

    private fun createToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val toast = Toast.makeText(this@RegisterActivity,
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