package fr.unice.etuarobasecanavaggio.myCasino

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var resultText1: TextView
    private lateinit var resultText2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configuration du padding pour Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialisation des vues
        resultText1 = findViewById(R.id.result_text1)
        resultText2 = findViewById(R.id.result_text2)

        // Configuration du bouton de lancer de dés
        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener { rollDice() }
    }

    private fun rollDice() {
        val randomInt1 = (1..6).random()
        val randomInt2 = (1..6).random()

        resultText1.text = randomInt1.toString()
        resultText2.text = randomInt2.toString()

        // Vérification de la victoire
        if (randomInt1 == randomInt2) {
            Toast.makeText(
                this,
                getString(R.string.congratulations),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this,
                getString(R.string.try_again),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}