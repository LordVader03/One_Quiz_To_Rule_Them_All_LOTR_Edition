package com.example.onequiztorulethemalllotredition.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onequiztorulethemalllotredition.R
import com.example.onequiztorulethemalllotredition.viewmodels.GameViewModel

class GameActivity : AppCompatActivity() {

    lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initDataBinding(){
        viewModel = GameViewModel()
    }

    private fun initLayout(){
        //activityGameBinding.question.text = viewModel.getQuestion()
    }
}