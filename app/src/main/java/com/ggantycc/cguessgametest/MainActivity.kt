package com.ggantycc.cguessgametest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ggantycc.cguessgametest.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var numberToGuess = 0

    var numberOfGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        numberToGuess = if (savedInstanceState != null && savedInstanceState.containsKey("KEY_RANDOM"))
        {
            savedInstanceState.getInt("KEY_RANDOM")
        } else {
            generateNewNumber()
        }
        Log.d("NumberToGuess", numberToGuess.toString())

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnReset.setOnClickListener {
            resetGame()
        }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY_RANDOM", numberToGuess)
    }

    fun generateNewNumber(): Int {
        return Random.nextInt(1, 10)
    }

    fun resetGame() {
        numberToGuess = generateNewNumber()
        numberOfGuesses = 0
        binding.etAnswer.setText("")
        binding.tvTipCounter.text = ""
        binding.tvResult.text = ""
        binding.btnGuess.isClickable = true
    }
}