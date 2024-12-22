package com.bubblepops.sponsorle

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bubblepops.sponsorle.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var balloonContainer: RelativeLayout
    private var bubbleCount = 0
    private val bubbleList = mutableListOf<ImageView>()
    private val maxBubbles = 12
    private val minBubbles = 5
    private val bubbleSizeInDp = 50
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        balloonContainer = binding.balloonContainer

        // Start the game
        startGame()
    }

    private fun startGame() {
        bubbleCount = 0
        binding.tvCount.text = "0"

        // Start the timer
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = (millisUntilFinished / 1000).toString()
                while (bubbleList.size < minBubbles) {
                    addBubble()
                }
                if (bubbleList.size < maxBubbles) {
                    addBubble()
                }
            }

            override fun onFinish() {
                endGame()
            }
        }.start()
    }

    private fun addBubble() {
        val bubbleSizeInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            bubbleSizeInDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        val newBubble = ImageView(this).apply {
            setImageResource(R.drawable.pngegg)
            layoutParams = RelativeLayout.LayoutParams(bubbleSizeInPx, bubbleSizeInPx)

            // Randomize position
            x = Random.nextInt(0, balloonContainer.width - bubbleSizeInPx).toFloat()
            y = Random.nextInt(0, balloonContainer.height - bubbleSizeInPx).toFloat()

            // Add animations
            val bubbleAnim = AnimationUtils.loadAnimation(this@GameActivity, R.anim.anim)
            startAnimation(bubbleAnim)

            // Handle popping
            setOnClickListener {
                popBubble(this)
            }
        }

        balloonContainer.addView(newBubble)
        bubbleList.add(newBubble)
    }

    private fun popBubble(bubble: ImageView) {
        bubble.setImageResource(R.drawable.boomclipart)
        bubble.isClickable = false
        bubble.clearAnimation()

        // Update score
        bubbleCount++
        binding.tvCount.text = bubbleCount.toString()

        // Remove bubble after animation
        bubble.postDelayed({
            if (bubble.parent != null) {
                balloonContainer.removeView(bubble)
                bubbleList.remove(bubble)
            }
        }, 500)
    }

    private fun endGame() {
        // Transition to ResultActivity with bubbleCount
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("bubbleCount", bubbleCount)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}
