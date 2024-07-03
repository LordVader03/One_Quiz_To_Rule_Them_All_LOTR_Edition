package com.example.onequiztorulethemalllotredition.models

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.callbackFlow

class Game {
    private val db = FirebaseDatabase.getInstance("https://oqtrtalotred-default-rtdb.europe-west1.firebasedatabase.app/").getReference("id")
    var questions: ArrayList<Question> = ArrayList()

    fun setQuestions(callback: () -> Unit) {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var questionList = ArrayList<Question>()
                for (idSnapshot in snapshot.children) {
                    val question = idSnapshot.child("Pregunta").getValue(String::class.java).toString()
                    val questionType = idSnapshot.child("Tipo").getValue(String::class.java).toString()
                    val answerList = ArrayList<Answer>()
                    for (respSnapshot in idSnapshot.child("Respuestas").children) {
                        val answerText = respSnapshot.key.toString()
                        val isCorrect = respSnapshot.getValue(Boolean::class.java) ?: false
                        answerList.add(Answer(answerText ?: "", isCorrect))
                    }
                    answerList.shuffle()
                    questionList.add(Question(question, questionType, answerList))
                }
                questionList.shuffle()
                questions = questionList
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun popFirstQuestion() {
        if (questions.isNotEmpty()) {
            questions.removeFirst()
        }
    }
}