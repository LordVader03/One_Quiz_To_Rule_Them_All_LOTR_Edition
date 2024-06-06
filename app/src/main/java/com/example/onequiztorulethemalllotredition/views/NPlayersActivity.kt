package com.example.onequiztorulethemalllotredition.views

import android.annotation.SuppressLint
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
    var nPlayersPrev = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nplayers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bundlePrev = intent.extras
        if(bundlePrev != null) {
            nPlayersPrev = bundlePrev.getInt("nPlayers")
        }
        backButton = findViewById<Button>(R.id.backNPlayersButton)
        homeButton = findViewById<Button>(R.id.homeNPlayersButton)
        lessButton = findViewById<Button>(R.id.lessNPlayersButton)
        moreButton = findViewById<Button>(R.id.moreNPlayersButton)
        nextButton = findViewById<Button>(R.id.nextNPlayersButton)
        nPlayers = findViewById<TextView>(R.id.nPlayersText)
        lessButton.visibility = View.INVISIBLE
        moreButton.visibility = View.VISIBLE
        if(nPlayersPrev != 0){
            lessButton.text = (nPlayersPrev - 1).toString()
            nPlayers.text = nPlayersPrev.toString()
            moreButton.text = (nPlayersPrev + 1).toString()
            lessButton.visibility = if(nPlayersPrev >= 3){
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            moreButton.visibility = if(nPlayersPrev <= 3){
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
        backButton.setOnClickListener{
            val i = Intent(this, PlayModeActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
        homeButton.setOnClickListener{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
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
            val i = Intent(this, NameActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("nPlayers", nPlayers.text.toString().toInt())
            bundle.putBoolean("direction", true)
            i.putExtras(bundle)
            startActivity(i)
            finishAffinity()
        }
    }
}