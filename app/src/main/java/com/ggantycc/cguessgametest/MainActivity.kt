package com.ggantycc.cguessgametest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ggantycc.cguessgametest.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val numberToGuess = Random.nextInt(1, 10)
        var numberOfGuesses = 0

        binding.btnGuess.setOnClickListener {
            numberOfGuesses++
            binding.tvTipCounter.setText("Number of tips: " + numberOfGuesses)
            val answeredNumber = binding.etAnswer.text
            if (answeredNumber.isNotEmpty()) {
                val rawValue = answeredNumber.toString().toInt()
                if (rawValue > 10 || rawValue < 1) {
                    binding.tvResult.setText("You can only guess a number between 1 and 10")
                } else {
                    if (rawValue > numberToGuess) {
                        binding.tvResult.setText("You guessed a higher number")
                    } else if (rawValue < numberToGuess) {
                        binding.tvResult.setText("You guessed a lower number")
                    } else {
                        binding.tvResult.setText("You guessed the number, you won!")
                        binding.btnGuess.isClickable = false
                    }
                }
            } else {
                binding.tvResult.setText("You did not guess any number")
            }
        }

    }
}