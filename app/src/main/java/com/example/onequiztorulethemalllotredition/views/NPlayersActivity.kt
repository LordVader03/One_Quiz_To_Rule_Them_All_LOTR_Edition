package com.example.onequiztorulethemalllotredition.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onequiztorulethemalllotredition.R

class NPlayersActivity : AppCompatActivity() {
    lateinit var backButton: Button
    lateinit var homeButton: Button
    lateinit var lessButton: Button
    lateinit var moreButton: Button
    lateinit var nextButton: Button
    lateinit var nPlayers: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nplayers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        backButton = findViewById<Button>(R.id.backNPlayersButton)
        homeButton = findViewById<Button>(R.id.homeNPlayersButton)
        lessButton = findViewById<Button>(R.id.lessNPlayersButton)
        moreButton = findViewById<Button>(R.id.moreNPlayersButton)
        nextButton = findViewById<Button>(R.id.nextNPlayersButton)
        nPlayers = findViewById<TextView>(R.id.nPlayersText)
        lessButton.visibility = View.INVISIBLE
        moreButton.visibility = View.VISIBLE
        backButton.setOnClickListener{
            val intent = Intent(this, PlayModeActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        homeButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        lessButton.setOnClickListener{
            var players = nPlayers.text.toString().toInt()
            players--
            if (players < 4){
                moreButton.visibility = View.VISIBLE
            }
            if (players < 3){
                lessButton.visibility = View.INVISIBLE
            }
            val oneMore = players + 1
            val oneLess = players - 1
            moreButton.text = oneMore.toString()
            lessButton.text = oneLess.toString()
            nPlayers.text = players.toString()
        }
        moreButton.setOnClickListener{
            var players = nPlayers.text.toString().toInt()
            players++
            if (players > 3){
                moreButton.visibility = View.INVISIBLE
            }
            if (players > 2){
                lessButton.visibility = View.VISIBLE
            }
            val oneMore = players + 1
            val oneLess = players - 1
            moreButton.text = oneMore.toString()
            lessButton.text = oneLess.toString()
            nPlayers.text = players.toString()
        }
        nextButton.setOnClickListener {
            val intent = Intent(this, NameActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}