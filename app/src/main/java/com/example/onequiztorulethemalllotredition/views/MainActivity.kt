package com.example.onequiztorulethemalllotredition.views

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onequiztorulethemalllotredition.R
import com.example.onequiztorulethemalllotredition.helpers.MusicManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        MusicManager.initialize(this, R.raw.inicio)
        MusicManager.play()
        val playButton = findViewById<Button>(R.id.playButton)
        val manualButton = findViewById<Button>(R.id.manualButton)
        playButton.setOnClickListener{
            MusicManager.release()
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
        manualButton.setOnClickListener{
            MusicManager.pause()
            val i = Intent(this, ManualActivity::class.java)
            startActivity(i)
        }
    }
}