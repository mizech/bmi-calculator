package com.example.bmirechner

import android.graphics.Color
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

        binding.messageTextView.setTextColor(Color.parseColor("#A52A2A"))
        var messageText = ""
        when {
            bmi < 20 -> { messageText = resources.getString(R.string.text_underweight) }
            bmi in 20.0..25.0 -> {
                messageText = resources.getString(R.string.text_normal_weight)
                binding.messageTextView.setTextColor(Color.parseColor("#006400"))
            }
            bmi > 25 && bmi <= 30 -> messageText = resources.getString(R.string.text_overweight)
            bmi > 30 -> {
                messageText = resources.getString(R.string.text_heavy_overweight)
                binding.messageTextView.setTextColor(Color.parseColor("#FF0000"))
            }
            else -> messageText = resources.getString(R.string.error)
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