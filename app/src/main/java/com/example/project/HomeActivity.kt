package com.example.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        if (fromRecording) {
            val data = intent.getParcelableExtra<MyDataModel>("data")
            val bundle = Bundle()
            bundle.putParcelable("data", data)
            val destinationFragment = TranslationFragment()
            destinationFragment.arguments = bundle

            replaceFragment(destinationFragment)
        } else {
            replaceFragment(HomeFragment())
        }
        binding!!.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.translation -> replaceFragment(TranslationFragment())
                R.id.recordings -> replaceFragment(RecordingFragment())
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
}