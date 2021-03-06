package com.example.bmirechner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.pow
import kotlin.math.round

class BmiViewModel: ViewModel() {
    private var currentHeight = MutableLiveData<Double>()
    private var currentWeight = MutableLiveData<Double>()

    init {
        currentHeight.value = 0.0
        currentWeight.value = 0.0
    }

    fun setCurrentHeight(value: Double) {
        currentHeight.value = value
    }

    fun setCurrentWeight(value: Double) {
        currentWeight.value = value
    }

    fun getCurrentHeight(): MutableLiveData<Double> {
        return currentHeight
    }

    fun getCurrentWeight(): MutableLiveData<Double> {
        return currentWeight
    }

    fun computeBmi(): Double {
        val factor = 10
        val heightInMeter = currentHeight.value!! / 100.0
        val bmi = currentWeight.value!! / heightInMeter.pow(2)
        return round(bmi * factor) / factor
    }
}