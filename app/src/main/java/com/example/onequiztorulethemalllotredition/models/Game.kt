package com.example.onequiztorulethemalllotredition.models

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class Game {
    private val db = FirebaseDatabase.getInstance("https://oqtrtalotred-default-rtdb.europe-west1.firebasedatabase.app/").getReference("id")
    private val storage = FirebaseStorage.getInstance("gs://oqtrtalotred.appspot.com").reference.child("/Imgs")
    var questions: ArrayList<Question> = ArrayList()

    fun setQuestions(callback: (List<Question>) -> Unit) {
        val snapshotListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val questionList = ArrayList<Question>()
                for (idSnapshot in snapshot.children) {
                    val question = idSnapshot.child("Pregunta").getValue(String::class.java).toString()
                    val questionType = idSnapshot.child("Tipo").getValue(String::class.java).toString()

                    val imageUrl = idSnapshot.child("Imagen").getValue(String::class.java).toString()
                    val storageRef = storage.child(imageUrl)
                    val downloadUrlTask = storageRef.downloadUrl

                    downloadUrlTask.addOnSuccessListener { uri ->
                        val imgUrl = uri.toString()
                        val answerList = ArrayList<Answer>()
                        for (respSnapshot in idSnapshot.child("Respuestas").children) {
                            val answerText = respSnapshot.key.toString()
                            val isCorrect = respSnapshot.getValue(Boolean::class.java) ?: false
                            answerList.add(Answer(answerText ?: "", isCorrect))
                        }
                        answerList.shuffle()

                        questionList.add(Question(question, questionType, imgUrl, answerList))

                        if (questionList.size == snapshot.childrenCount.toInt()) {
                            questionList.shuffle()
                            questions = questionList
                            callback(questions)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        db.addListenerForSingleValueEvent(snapshotListener)
    }

    fun popFirstQuestion() {
        if (questions.isNotEmpty()) {
            questions.removeFirst()
        }
    }
}