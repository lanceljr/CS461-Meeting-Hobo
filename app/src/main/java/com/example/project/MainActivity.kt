package com.example.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project.databinding.ActivityMainBinding

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
    }
}