package fr.unice.etuarobasecanavaggio.myCasino

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var resultText1: TextView
    private lateinit var resultText2: TextView
    private lateinit var targetValue: TextView
    private lateinit var sumText: TextView
    private lateinit var rollButton: Button
    private lateinit var targetSeekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultText1 = findViewById(R.id.result_text1)
        resultText2 = findViewById(R.id.result_text2)
        targetValue = findViewById(R.id.target_value)
        sumText = findViewById(R.id.sum_text)
        rollButton = findViewById(R.id.roll_button)
        targetSeekBar = findViewById(R.id.target_seekbar)

        targetSeekBar.min = 2
        targetSeekBar.max = 12
        targetSeekBar.progress = 7

        targetSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                targetValue.text = progress.toString()
                rollButton.isEnabled = true
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        rollButton.setOnClickListener { rollDice() }
    }

    private fun rollDice() {
        val randomInt1 = (1..6).random()
        val randomInt2 = (1..6).random()
        val sum = randomInt1 + randomInt2

        resultText1.text = randomInt1.toString()
        resultText2.text = randomInt2.toString()
        sumText.text = getString(R.string.sum_format, sum)

        val targetNum = targetValue.text.toString().toInt()
        if (sum == targetNum) {
            Toast.makeText(
                this,
                getString(R.string.win_message, sum),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this,
                getString(R.string.lose_message, sum, targetNum),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}