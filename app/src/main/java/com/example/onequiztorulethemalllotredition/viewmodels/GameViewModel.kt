package com.example.onequiztorulethemalllotredition.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onequiztorulethemalllotredition.models.Game
import com.example.onequiztorulethemalllotredition.models.Question

class GameViewModel : ViewModel() {
    private val game = Game()
    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        game.setQuestions {
            _questions.value = game.questions
        }
    }

    fun checkAnswer(answerIndex: Int, callback: (Boolean) -> Unit) {
        if (questions.value.isNullOrEmpty()) return

        val currentQuestion = questions.value!!.first()
        val isCorrect = currentQuestion.answerList[answerIndex].answerValue
        callback(isCorrect)
    }

    fun loadNextQuestion() {
        game.popFirstQuestion()
        _questions.value = game.questions
    }

    fun resetGame() {
        loadQuestions()
    }
}