package fr.unice.etuarobasecanavaggio.myCasino

import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var diceImage1: ImageView
    private lateinit var diceImage2: ImageView
    private lateinit var brookImage: ImageView
    private lateinit var targetValue: TextView
    private lateinit var sumText: TextView
    private lateinit var targetSeekBar: SeekBar

    private val diceImages = arrayOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        diceImage1 = findViewById(R.id.dice_image1)
        diceImage2 = findViewById(R.id.dice_image2)
        brookImage = findViewById(R.id.brook_image)
        targetValue = findViewById(R.id.target_value)
        sumText = findViewById(R.id.sum_text)
        targetSeekBar = findViewById(R.id.target_seekbar)

        targetSeekBar.min = 2
        targetSeekBar.max = 12
        targetSeekBar.progress = 7

        targetSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                targetValue.text = progress.toString()
                if (fromUser) {
                    hideWinAnimation()
                    rollDice()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun rollDice() {
        val randomInt1 = (1..6).random()
        val randomInt2 = (1..6).random()
        val sum = randomInt1 + randomInt2

        diceImage1.setImageResource(diceImages[randomInt1 - 1])
        diceImage2.setImageResource(diceImages[randomInt2 - 1])
        sumText.text = getString(R.string.sum_format, sum)

        val targetNum = targetValue.text.toString().toInt()
        if (sum == targetNum) {
            Toast.makeText(
                this,
                getString(R.string.win_message, sum),
                Toast.LENGTH_LONG
            ).show()
            animateWin()
        } else {
            Toast.makeText(
                this,
                getString(R.string.lose_message, sum, targetNum),
                Toast.LENGTH_SHORT
            ).show()
            hideWinAnimation()
        }
    }
    private fun hideWinAnimation() {
        brookImage.visibility = View.GONE
    }

    private fun animateWin() {
        val bounceAnim1 = ObjectAnimator.ofFloat(diceImage1, View.TRANSLATION_Y, 0f, -100f, 0f)
        bounceAnim1.duration = 1000
        bounceAnim1.interpolator = AccelerateDecelerateInterpolator()

        val bounceAnim2 = ObjectAnimator.ofFloat(diceImage2, View.TRANSLATION_Y, 0f, -100f, 0f)
        bounceAnim2.duration = 1000
        bounceAnim2.interpolator = AccelerateDecelerateInterpolator()

        val rotateAnim1 = ObjectAnimator.ofFloat(diceImage1, View.ROTATION, 0f, 360f)
        rotateAnim1.duration = 1000

        val rotateAnim2 = ObjectAnimator.ofFloat(diceImage2, View.ROTATION, 0f, 360f)
        rotateAnim2.duration = 1000

        brookImage.visibility = View.VISIBLE

        val brookFadeIn = ObjectAnimator.ofFloat(brookImage, View.ALPHA, 0f, 1f)
        brookFadeIn.duration = 500

        val brookBounce = ObjectAnimator.ofFloat(brookImage, View.TRANSLATION_Y, -50f, 0f)
        brookBounce.duration = 500
        brookBounce.interpolator = AccelerateDecelerateInterpolator()

        AnimatorSet().apply {
            playTogether(bounceAnim1, bounceAnim2, rotateAnim1, rotateAnim2,brookFadeIn, brookBounce)
            start()
        }
    }
}