package com.example.bmirechner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.pow
import kotlin.math.round

class BmiViewModel: ViewModel() {
    private var currentHeight = MutableLiveData<Double>()
    private var currentWeight = MutableLiveData<Double>()

    init {
        currentHeight.value = 150.0
        currentWeight.value = 50.0
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
        val factor = 100
        val heightInMeter = currentHeight.value!! / factor
        val bmi = currentWeight.value!! / heightInMeter.pow(2)
        return round(bmi * factor) / factor
    }
}