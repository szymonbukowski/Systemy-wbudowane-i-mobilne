package com.example.bmi.logic

import android.content.Context
import android.graphics.Color
import android.provider.Settings.Global.getString
import androidx.core.content.ContextCompat.getColor


import com.example.bmi.R

class BmiDescriber(val bmi:Bmi, val context: Context){

    fun getBmiColor() = when(bmi.countBmi()){

            in  0.0 .. 16.0 -> getColor(context,R.color.roz_pompejanski)
            in 16.0 .. 18.5 -> getColor(context,R.color.lapis_lazuli)
            in 18.5 .. 25.0 -> getColor(context,R.color.grynszpan)
            in 25.0 .. 30.0 -> getColor(context,R.color.lapis_lazuli)
            in 30.0 .. 35.0 -> getColor(context,R.color.roz_pompejanski)
            in 35.0 .. Double.POSITIVE_INFINITY-> getColor(context,R.color.kasztanowaty)
            else -> Color.BLACK
    }



    fun getBmiCategory() = when(bmi.countBmi()){
            in  0.0 .. 16.0 -> R.string.severly_underweight
            in 16.0 .. 18.5 ->R.string.underweight
            in 18.5 .. 25.0 -> R.string.normal_weight
            in 25.0 .. 30.0 -> R.string.overweight
            in 30.0 .. 35.0 ->R.string.obese1
            in 35.0 .. Double.POSITIVE_INFINITY-> R.string.totaly_obese
            else -> R.string.normal_weight
    }

    fun getBmiDescription() =
        when(bmi.countBmi()){

            in  0.0 .. 16.0 -> R.string.severly_underweight
            in 16.0 .. 18.5 -> R.string.underweight
            in 18.5 .. 25.0 -> R.string.normal_weight
            in 25.0 .. 30.0 -> R.string.overweight
            in 30.0 .. 35.0 -> R.string.obese1
            in 35.0 .. Double.POSITIVE_INFINITY-> R.string.totaly_obese
            else -> R.string.normal_weight

    }
}