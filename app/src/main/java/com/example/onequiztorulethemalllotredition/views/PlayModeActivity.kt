package com.example.onequiztorulethemalllotredition.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onequiztorulethemalllotredition.R

class PlayModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_play_mode)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val backButton = findViewById<Button>(R.id.backButton1)
        val playerButton = findViewById<Button>(R.id.competitiveModeButton)
        backButton.setOnClickListener{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
        playerButton.setOnClickListener{
            val i = Intent(this, NPlayersActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
    }
}