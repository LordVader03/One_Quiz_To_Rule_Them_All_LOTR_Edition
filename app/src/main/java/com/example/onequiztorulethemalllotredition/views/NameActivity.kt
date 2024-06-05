package com.example.onequiztorulethemalllotredition.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onequiztorulethemalllotredition.R

class NameActivity : AppCompatActivity() {
    lateinit var homeButton: Button
    lateinit var backButton: Button
    lateinit var nextButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_name)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        backButton = findViewById<Button>(R.id.backNameButton)
        homeButton = findViewById<Button>(R.id.homeNPlayersButton)
        nextButton = findViewById<Button>(R.id.doneNameButton)
        backButton.setOnClickListener {
            val intent = Intent(this, NPlayersActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        nextButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}