package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bmi.MainActivity.Companion

class BmiInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_info)
    }

    fun printInfo(){
        val bmiResult = intent.getDoubleExtra(Companion.bmiResult)
        val bmiCategory = intent.getStringExtra(Companion.bmiCategory)

    }



}
