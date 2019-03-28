package com.example.bmi.logic

class BmiForKgCm(var mass: Int, var height: Int) : Bmi{

    override fun countBmi(): Double = mass*10000.0 / (height*height)

}