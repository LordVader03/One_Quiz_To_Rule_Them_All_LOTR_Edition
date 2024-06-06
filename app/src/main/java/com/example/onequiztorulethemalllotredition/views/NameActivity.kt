package com.example.onequiztorulethemalllotredition.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onequiztorulethemalllotredition.R
import java.util.ArrayList

class NameActivity : AppCompatActivity() {
    lateinit var homeButton: Button
    lateinit var backButton: Button
    lateinit var nextButton: Button
    lateinit var playerName: EditText
    lateinit var playerEnunciate: TextView
    private var nPlayers: Int = 0
    private var nOriginalPlayers: Int = 0
    private var playerNames: ArrayList<String> = ArrayList()
    private var direction: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_name)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundlePrev = intent.extras
        if (bundlePrev != null) {
            nPlayers = bundlePrev.getInt("nPlayers")
            nOriginalPlayers = bundlePrev.getInt("nOriginalPlayers", nPlayers)
            direction = bundlePrev.getBoolean("direction", true)
            playerNames = bundlePrev.getStringArrayList("playerNames") ?: ArrayList()
        }

        backButton = findViewById(R.id.backNameButton)
        homeButton = findViewById(R.id.homeNPlayersButton)
        nextButton = findViewById(R.id.doneNameButton)
        playerName = findViewById(R.id.playerNameText)
        playerEnunciate = findViewById(R.id.playerTextEnunciate)

        updatePlayerEnunciate()

        if (playerNames.isNotEmpty() && !direction) {
            playerName.setText(playerNames.last())
        }

        backButton.setOnClickListener {
            handleBackButton()
        }
        homeButton.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
        nextButton.setOnClickListener {
            handleNextButton()
        }
    }

    private fun updatePlayerEnunciate() {
        playerEnunciate.text = if (direction) {
            getString(R.string.player_enunciate, playerNames.size + 1)
        } else {
            getString(R.string.player_enunciate, playerNames.size)
        }
    }

    private fun handleBackButton() {
        val i: Intent
        val bundleNext = Bundle()
        if(!direction){
            playerNames.removeAt(playerNames.size - 1)
            nPlayers++
        }
        bundleNext.putInt("nPlayers", nPlayers)
        i = if (playerNames.isNotEmpty()) {
            bundleNext.putStringArrayList("playerNames", playerNames)
            bundleNext.putInt("nOriginalPlayers", nOriginalPlayers)
            bundleNext.putBoolean("direction", false)
            Intent(this, NameActivity::class.java)
        } else {
            Intent(this, NPlayersActivity::class.java)
        }
        i.putExtras(bundleNext)
        startActivity(i)
        finishAffinity()
    }

    private fun handleNextButton() {
        val bundleNext = Bundle()
        if(direction) {
            playerNames.add(playerName.text.toString())
        } else {
            playerNames.set(playerNames.size - 1, playerName.text.toString())
        }
        if(direction) {
            nPlayers--
        }
        bundleNext.putStringArrayList("playerNames", playerNames)
        bundleNext.putInt("nPlayers", nPlayers)
        bundleNext.putInt("nOriginalPlayers", nOriginalPlayers)
        bundleNext.putBoolean("direction", true)

        val i: Intent = if (nPlayers > 0) {
            Intent(this, NameActivity::class.java)
        } else {
            Intent(this, GameActivity::class.java)
        }

        i.putExtras(bundleNext)
        startActivity(i)
        finishAffinity()
    }
}
