package com.bubblepops.sponsorle

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bubblepops.sponsorle.databinding.ActivityMainBinding
import com.google.android.material.card.MaterialCardView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val progressBarDuration: Long = Random.nextLong(2000, 2500)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startUpDownAnimation()
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.outerCardView.post {
            val finalWidth = binding.outerCardView.width
            animateProgress(binding.innerCardView, finalWidth, progressBarDuration)

            // Navigate to HomeActivity after 3 seconds
            binding.outerCardView.postDelayed({
                binding.outerCardView.visibility = View.GONE
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            }, 3000)
        }

    }


    private fun startUpDownAnimation() {
        // Animate ivLogo to move up and down
        val animator = ObjectAnimator.ofFloat(
            binding.ivLogo, // Target the ivLogo ImageView
            "translationY", // Move vertically
            -50f, 50f // Values to animate (up: -50f, down: 50f)
        ).apply {
            duration = 1000 // 1 second for one direction
            repeatMode = ObjectAnimator.REVERSE // Reverse direction after reaching end
            repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        }

        animator.start() // Start the animation
    }

    private fun animateProgress(cardView: MaterialCardView, finalWidth: Int, duration: Long) {
        val startWidth = 0
        val layoutParams = cardView.layoutParams as ViewGroup.LayoutParams

        val handler = Handler(Looper.getMainLooper())
        val startTime = System.currentTimeMillis()

        handler.post(object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                val progress = elapsedTime.toFloat() / duration
                layoutParams.width = (startWidth + (finalWidth * progress)).toInt()
                cardView.layoutParams = layoutParams

                if (progress < 1.0f) {
                    handler.postDelayed(this, 16) // Continue animation every 16ms (~60fps)
                } else {
                    // Ensure full width is set at the end of the animation
                    layoutParams.width = finalWidth
                    cardView.layoutParams = layoutParams

                    // Delay and navigate after 3 seconds
                    handler.postDelayed({
                        binding.outerCardView.visibility = View.GONE
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        finish()
                    }, 3000)
                }
            }
        })
    }


}



