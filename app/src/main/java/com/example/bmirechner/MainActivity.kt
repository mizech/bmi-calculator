package com.example.bmirechner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bmirechner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BmiViewModel

    fun updateUi() {
        val bmi = viewModel.computeBmi()
        binding.resultTextView.text = bmi.toString()

        var messageText = ""
        when {
            bmi < 20 -> messageText = "Sie haben Untergewicht."
            bmi >= 20 && bmi <=25 -> messageText = "Sie haben Normalgewicht."
            bmi > 25 && bmi <= 30 -> messageText = "Sie haben Übergewicht."
            bmi > 30 -> messageText = "Sie haben starkes Übergewicht."
            else -> messageText = "Fehler!"
        }

        binding.messageTextView.text = messageText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(BmiViewModel::class.java)

        binding.currentWeightTextView.text = viewModel.getCurrentWeight().value.toString()
        binding.currentHeightTextView.text = viewModel.getCurrentHeight().value.toString()

        binding.heightSeek.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setCurrentHeight(progress.toDouble())
                viewModel.getCurrentHeight().observe(this@MainActivity, Observer {
                    binding.currentHeightTextView.text = it.toString()
                })
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                updateUi()
            }
        })

        binding.weightSeek.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setCurrentWeight(progress.toDouble())
                viewModel.getCurrentWeight().observe(this@MainActivity, Observer {
                    binding.currentWeightTextView.text = it.toString()
                })
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                updateUi()
            }
        })
    }
}