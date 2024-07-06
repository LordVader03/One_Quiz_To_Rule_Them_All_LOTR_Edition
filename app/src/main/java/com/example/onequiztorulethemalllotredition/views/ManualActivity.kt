package com.example.onequiztorulethemalllotredition.views

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onequiztorulethemalllotredition.R
import com.example.onequiztorulethemalllotredition.helpers.MusicManager

class ManualActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_manual)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.manual)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            mediaPlayer?.release()
            finish()
            MusicManager.play()
        }
    }
}