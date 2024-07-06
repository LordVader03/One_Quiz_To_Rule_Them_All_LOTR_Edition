package com.example.onequiztorulethemalllotredition.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.media.MediaPlayer
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.onequiztorulethemalllotredition.R
import com.example.onequiztorulethemalllotredition.models.Question
import com.example.onequiztorulethemalllotredition.viewmodels.GameViewModel
import com.example.onequiztorulethemalllotredition.helpers.MusicManager

class GameActivity : AppCompatActivity() {

    private lateinit var viewModel: GameViewModel

    private var score1 = 0
    private var score2 = 0
    private var score3 = 0
    private var scoreGlobal = 0

    private var timeRemaining = 10
    private lateinit var timerHandler: Handler
    private lateinit var timerRunnable: Runnable

    private var isFirstLoad = true
    private var resultPlayer: MediaPlayer? = null

    private lateinit var questionText: TextView
    private lateinit var response1Button:Button
    private lateinit var response2Button:Button
    private lateinit var response3Button:Button
    private lateinit var response4Button:Button
    private lateinit var playAgainButton: Button
    private lateinit var manualButton: Button
    private lateinit var pointText1: TextView
    private lateinit var pointText2: TextView
    private lateinit var pointText3: TextView
    private lateinit var resultText: TextView
    private lateinit var questionImg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        questionText = findViewById(R.id.questionText)
        response1Button = findViewById(R.id.response1Button)
        response2Button = findViewById(R.id.response2Button)
        response3Button = findViewById(R.id.response3Button)
        response4Button = findViewById(R.id.response4Button)
        playAgainButton = findViewById(R.id.playAgainButton)
        manualButton = findViewById(R.id.manualFromPlayButton)
        pointText1 = findViewById(R.id.pointMarker1Text)
        pointText2 = findViewById(R.id.pointMarker2Text)
        pointText3 = findViewById(R.id.pointMarker3Text)
        resultText = findViewById(R.id.resultText)
        questionImg = findViewById(R.id.questionImg)

        response1Button.visibility = View.INVISIBLE
        response2Button.visibility = View.INVISIBLE
        response3Button.visibility = View.INVISIBLE
        response4Button.visibility = View.INVISIBLE
        resultText.visibility = View.INVISIBLE
        playAgainButton.visibility = View.INVISIBLE
        manualButton.visibility = View.INVISIBLE
        pointText1.visibility = View.INVISIBLE
        pointText2.visibility = View.INVISIBLE
        pointText3.visibility = View.INVISIBLE

        if (isFirstLoad) {
            questionText.text = "Are you ready for the challenge?"
            questionImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.awaiting))
        }

        viewModel.questions.observe(this) { questions ->
            if (questions.isNotEmpty()) {
                displayQuestion(questions.first())
            }
        }

        response1Button.setOnClickListener { checkAnswer(0, viewModel.questions.value?.first()!!.questionType) }
        response2Button.setOnClickListener { checkAnswer(1, viewModel.questions.value?.first()!!.questionType) }
        response3Button.setOnClickListener { checkAnswer(2, viewModel.questions.value?.first()!!.questionType) }
        response4Button.setOnClickListener { checkAnswer(3, viewModel.questions.value?.first()!!.questionType) }
        playAgainButton.setOnClickListener { replay() }
        manualButton.setOnClickListener {
            MusicManager.pause()
            val i = Intent(this, ManualActivity::class.java)
            startActivity(i)
        }
    }

    private fun displayQuestion(question: Question) {
        if (isFirstLoad) {
            questionText.text = "Are you ready for the challenge?"
            questionImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.awaiting))
            MusicManager.initialize(this, R.raw.awaiting)
            MusicManager.play()
        }
        questionImg.load(question.imageUrl) {
            listener(
                onSuccess = { _, _ ->
                    if (isFirstLoad) {
                        response1Button.visibility = View.VISIBLE
                        response2Button.visibility = View.VISIBLE
                        response3Button.visibility = View.VISIBLE
                        response4Button.visibility = View.VISIBLE
                        questionImg.visibility = View.VISIBLE
                        resultText.visibility = View.VISIBLE
                    }
                    questionText.text = question.question
                    response1Button.text = question.answerList[0].answer
                    response2Button.text = question.answerList[1].answer
                    response3Button.text = question.answerList[2].answer
                    response4Button.text = question.answerList[3].answer

                    val buttonColor = when (question.questionType) {
                        "Movies" -> "#FF6400"
                        "Events" -> "#00C850"
                        "Characters" -> "#0064FF"
                        else -> null
                    }
                    buttonColor?.let {
                        response1Button.setBackgroundColor(Color.parseColor(it))
                        response2Button.setBackgroundColor(Color.parseColor(it))
                        response3Button.setBackgroundColor(Color.parseColor(it))
                        response4Button.setBackgroundColor(Color.parseColor(it))
                    }

                    isFirstLoad = false

                    timeRemaining = 10
                    startTimer()
                    updateTimerDisplay()
                },
                onError = { _, throwable ->
                }
            )
        }
    }

    private fun checkAnswer(answerIndex: Int, questionType: String) {
        timerHandler.removeCallbacks(timerRunnable)
        viewModel.checkAnswer(answerIndex) { isCorrect ->
            if (isCorrect) {
                scoreGlobal++
                when (questionType) {
                    "Movies" -> {
                        score1++
                        pointText1.text = score1.toString()
                    }
                    "Events" -> {
                        score2++
                        pointText2.text = score2.toString()
                    }
                    "Characters" -> {
                        score3++
                        pointText3.text = score3.toString()
                    }
                }
                resultText.text = "Correct!"
                questionImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.correct))
                resultPlayer?.release()
                resultPlayer = MediaPlayer.create(this, R.raw.correct)
                resultPlayer?.start()
            } else {
                pointText1.text = score1.toString()
                pointText2.text = score2.toString()
                pointText3.text = score3.toString()
                resultText.text = "Incorrect!"
                questionImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.incorrect))
                resultPlayer?.release()
                resultPlayer = MediaPlayer.create(this, R.raw.incorrect)
                resultPlayer?.start()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.loadNextQuestion()
                if (viewModel.questions.value.isNullOrEmpty()) {
                    showGameOver()
                } else if (score1 >= 1 && score2 >= 1 && score3 >= 1) {
                    showGameOver()
                }
            }, 1500)
        }
    }

    private fun showGameOver() {
        pointText1.visibility = View.INVISIBLE
        pointText2.visibility = View.INVISIBLE
        pointText3.visibility = View.INVISIBLE
        response1Button.visibility = View.INVISIBLE
        response2Button.visibility = View.INVISIBLE
        response3Button.visibility = View.INVISIBLE
        response4Button.visibility = View.INVISIBLE
        resultText.visibility = View.INVISIBLE
        if(score1 == 1 && score2 == 1 && score3 == 1){
            questionText.text = ("You win!\n Your score: $scoreGlobal\n Movies: $score1, Events: $score2,\n Characters: $score3")
            questionImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.victory))
            MusicManager.changeTrack(this, R.raw.victory)
        } else {
            questionText.text = ("You lose!\n Your score: $scoreGlobal\n Movies: $score1, Events: $score2,\n Characters: $score3")
            questionImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.defeat))
            MusicManager.changeTrack(this, R.raw.defeat)
        }
        playAgainButton.visibility = View.VISIBLE
        manualButton.visibility = View.VISIBLE
    }

    private fun replay() {
        viewModel.resetGame()
        MusicManager.release()
        scoreGlobal = 0
        score1 = 0
        score2 = 0
        score3 = 0
        timeRemaining = 10
        isFirstLoad = true

        viewModel.questions.observe(this) { questions ->
            if (questions.isNotEmpty()) {
                playAgainButton.visibility = View.INVISIBLE
                manualButton.visibility = View.INVISIBLE
                pointText1.visibility = View.VISIBLE
                pointText2.visibility = View.VISIBLE
                pointText3.visibility = View.VISIBLE
                pointText1.text = score1.toString()
                pointText2.text = score2.toString()
                pointText3.text = score3.toString()
                resultText.visibility = View.VISIBLE
            }
        }
    }

    private fun startTimer() {
        timerHandler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            override fun run() {
                if (timeRemaining > 1) {
                    timeRemaining--
                    updateTimerDisplay()
                    timerHandler.postDelayed(this, 1000)
                } else {
                    handleTimeout()
                }
            }
        }
        timerHandler.postDelayed(timerRunnable, 1000)
    }

    private fun updateTimerDisplay() {
        resultText.text = "Time: $timeRemaining"
    }

    private fun handleTimeout() {
        resultText.text = "Time's up!"
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.loadNextQuestion()
            if (viewModel.questions.value.isNullOrEmpty()) {
                showGameOver()
            }
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timerHandler.removeCallbacks(timerRunnable)
        MusicManager.release()
        resultPlayer?.release()
    }
}