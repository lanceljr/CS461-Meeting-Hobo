package com.example.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    var binding: ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        replaceFragment(HomeFragment())
//        binding!!.bottomNavigationView.setBackground(null)
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
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}