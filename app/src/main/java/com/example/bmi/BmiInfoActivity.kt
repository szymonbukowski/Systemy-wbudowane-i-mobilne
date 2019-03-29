package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bmi.MainActivity.Companion
import kotlinx.android.synthetic.main.bmi_info.*

class BmiInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_info)
        printInfo()
    }

    fun printInfo(){
        val bmiResult = intent.getStringExtra(Companion.bmiResult)
        val bmiCategory = intent.getStringExtra(Companion.bmiCategory)
        val resultColor = intent.getIntExtra(Companion.resultColor,0)

        inf_BmiResult.setText(bmiResult)
        inf_BmiResult.setTextColor(resultColor)

        val description = when(bmiCategory){
            getString(R.string.severely_underweight)-> getString(R.string.severely_underweight_description)
            getString(R.string.underweight) -> getString(R.string.underweight_description)
            getString(R.string.normal_weight) -> getString(R.string.normal_weight_description)
            getString(R.string.overweight) -> getString(R.string.overweight_description)
            getString(R.string.obese1) ->getString(R.string.obese1_description)
            getString(R.string.obese2) -> getString(R.string.obese2_description)
            getString(R.string.totaly_obese) -> getString(R.string.totaly_obese_description)
            else -> getString(R.string.normal_weight)
        }

        inf_bmiDescription.text = description
    }



}
