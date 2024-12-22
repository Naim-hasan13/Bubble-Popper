package com.bubblepops.sponsorle

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bubblepops.sponsorle.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startUpDownAnimation()
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bubbleCount = intent.getIntExtra("bubbleCount", 0)

        // Display the count
        binding.tvCount.text = "x $bubbleCount"
        binding.ivCode.setOnClickListener {
            val intent = Intent(this, RedeemActivity::class.java)
            startActivity(intent)
        }

        binding.ivRedeem.setOnClickListener {
            val intent = Intent(this, Withdrawal_Activity::class.java)
            startActivity(intent)
        }
    }

    private fun startUpDownAnimation() {
        // Animate ivLogo to move up and down
        val animator = ObjectAnimator.ofFloat(
            binding.ivLogo1, // Target the ivLogo ImageView
            "translationY", // Move vertically
            -30f, 30f // Values to animate (up: -50f, down: 50f)
        ).apply {
            duration = 1000 // 1 second for one direction
            repeatMode = ObjectAnimator.REVERSE // Reverse direction after reaching end
            repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        }

        animator.start() // Start the animation
    }
}