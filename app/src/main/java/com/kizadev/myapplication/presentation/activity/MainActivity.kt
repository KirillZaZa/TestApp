package com.kizadev.myapplication.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kizadev.myapplication.R
import com.kizadev.myapplication.presentation.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MainFragment())
            .commit()
    }
}
