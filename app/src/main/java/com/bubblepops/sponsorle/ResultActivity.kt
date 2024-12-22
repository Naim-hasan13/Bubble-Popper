package com.bubblepops.sponsorle

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bubblepops.sponsorle.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startUpDownAnimation()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Get the bubbleCount from the Intent
        val bubbleCount = intent.getIntExtra("bubbleCount", 0)

        // Display the count
        binding.count.text = "$bubbleCount"

        // Retry button: Restart the game
        binding.ivRetry.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Home button: Return to home activity
        binding.ivHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startUpDownAnimation() {
        // Animate ivLogo to move up and down
        val animator = ObjectAnimator.ofFloat(
            binding.ivLogo2, // Target the ivLogo ImageView
            "translationY", // Move vertically
            -50f, 50f // Values to animate (up: -50f, down: 50f)
        ).apply {
            duration = 1000 // 1 second for one direction
            repeatMode = ObjectAnimator.REVERSE // Reverse direction after reaching end
            repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        }

        animator.start() // Start the animation
    }
}
