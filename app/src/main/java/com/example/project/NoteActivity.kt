package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

class NoteActivity : AppCompatActivity() {
    private var noteIsNew : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val intent = intent
        val data = intent.getParcelableExtra<Note>("data")
        if (data != null) {
            val editTextView = findViewById<EditText>(R.id.editNoteName)
            editTextView.text = data.title as Editable
            findViewById<EditText>(R.id.editNoteDesc).text = data.notes as Editable
        } else {
            noteIsNew = true
        }

    }

    fun saveNote(view: View) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("toNotes", true)
        startActivity(intent)
    }
}