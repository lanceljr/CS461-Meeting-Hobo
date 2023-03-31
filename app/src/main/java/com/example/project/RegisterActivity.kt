package com.example.project

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
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
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("email", findViewById<EditText>(R.id.editTextEmail).text.toString())
                .add("password", findViewById<EditText>(R.id.editTextPassword).text.toString())
                .build()


            val request = Request.Builder()
                .url("http://127.0.0.1/register")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    createToast("There has been an error. Please try again!")

                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    if (response.code == 400) {
                        createToast("User has already been created with this email. Please use another one!")
                    } else {
                        val it = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(it)
                    }
                }
            })
        }

    }

    private fun createToast(message: String) {
        val toast = Toast.makeText(this@RegisterActivity,
            message,
            Toast.LENGTH_LONG)
        val toastView = toast.view
        toastView?.setBackgroundColor(Color.RED)
        val text = toastView?.findViewById(android.R.id.message) as TextView
        text.setTextColor(Color.WHITE)
        toast.show()
    }
}