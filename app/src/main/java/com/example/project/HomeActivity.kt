package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    var binding: ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        val intent = intent
        val fromRecording = intent.getBooleanExtra("fromRecording", false)
        val toNotes = intent.getBooleanExtra("toNotes", false)
        val toRecordings = intent.getBooleanExtra("goToRecordings", false)
        if (fromRecording) {
            val data = intent.getParcelableExtra<MyDataModel>("data")
            val bundle = Bundle()
            bundle.putParcelable("data", data)
            val destinationFragment = TranslationFragment()
            destinationFragment.arguments = bundle

            replaceFragment(destinationFragment)
            binding!!.bottomNavigationView.selectedItemId = R.id.translation
        } else if (toNotes) {
            replaceFragment(NotesFragment())
            binding!!.bottomNavigationView.selectedItemId = R.id.notes
        } else if (toRecordings) {
            goToRecordings()
        } else {
            replaceFragment(HomeFragment())
            binding!!.bottomNavigationView.selectedItemId = R.id.home
        }
        binding!!.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.translation -> replaceFragment(TranslationFragment())
                R.id.recordings -> replaceFragment(RecordingsFragment())
                R.id.notes -> replaceFragment(NotesFragment())
            }
            true
        }
//        binding!!.bottomNavigationView.setBackground(null)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    fun goToRecordings() {
        replaceFragment(RecordingsFragment())
        binding!!.bottomNavigationView.selectedItemId = R.id.frame_recordings
    }

    fun goToRecording() {
        print("called")
        val intent = Intent(this, RecordActivity::class.java)
        intent.putExtra("newRecording", true)
        startActivity(intent)
    }

    fun goToNoteActivity() {
        
        val intent = Intent(this, NoteActivity::class.java)
//        intent.putExtra("data", null)
        startActivity(intent)
    }
}