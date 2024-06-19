package com.example.onequiztorulethemalllotredition.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GameViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    private val db = FirebaseDatabase.getInstance("https://oqtrtalotred-default-rtdb.europe-west1.firebasedatabase.app/").getReference("id")

    init {
        fetchItemsFromRealTimeDatabase()
    }

    private fun fetchItemsFromRealTimeDatabase() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = snapshot.children.mapNotNull { childSnapshot ->
                    mapToItem(childSnapshot)
                }
                _items.value = itemList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors (log, display message, etc.)
            }
        })
    }

    private fun mapToItem(snapshot: DataSnapshot): Item? {
        return try {
            val preguntaSnapshot = snapshot.child("Pregunta")
            val respuestasSnapshot = snapshot.child("Respuestas")

            Item(
                id = snapshot.key ?: "",
                pregunta = preguntaSnapshot.getValue(Pregunta::class.java),
                respuestas = respuestasSnapshot.getValue(Respuestas::class.java)
            )
        } catch (e: Exception) {
            // Handle mapping errors if needed
            null
        }
    }
}

data class Pregunta(
    val pregunta: String = ""
)

data class Respuestas(
    val respuesta1: Boolean = false,
    val respuesta2: Boolean = false,
    val respuesta3: Boolean = false,
    val respuesta4: Boolean = false
)

data class Item(
    val id: String = "",
    val pregunta: Pregunta? = null,
    val respuestas: Respuestas? = null
)