package com.example.bmi.logic

class BmiForLbIn(val mass:Int, val height:Int) : Bmi {

    override fun countBmi(): Double = 703.0 * mass / (height * height)
}