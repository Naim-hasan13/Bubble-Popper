package com.bubblepops.sponsorle

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bubblepops.sponsorle.databinding.ActivityHomeBinding
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startUpDownAnimation()
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivPlay.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        binding.ivMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
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
}
