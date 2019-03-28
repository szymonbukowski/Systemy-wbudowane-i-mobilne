package com.example.bmi

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.bmi.logic.BmiForKgCm
import com.example.bmi.logic.BmiForLbIn
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CountButton.setOnClickListener { countBmi() }
        bmiInfoFAB.setOnClickListener { openBmiInfo() }
        bmiInfoFAB.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.title == getString(R.string.about_me) -> {
                val aboutMeIntent = Intent(this, AboutMeActivity::class.java)
                startActivity(aboutMeIntent)
            }
            item?.title == getString(R.string.switch_units) -> {
                switchUnits()
            }
            item?.title == getString(R.string.history) -> {
                val historyIntent = Intent(this, HistoryActivity::class.java)
                startActivity(historyIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun isInputValid():Boolean {
        val massb = isMassValid()
        val heightb = isHeightValid()
        return (massb && heightb)
    }
    private fun isMassValid():Boolean{
        when{
            isMassEmpty() -> {
                MassInput.error = getString(R.string.mass_err_empty)
                return false
            }
            isMassTooHigh() -> {
                MassInput.error = getString(R.string.mass_err_high)
                return false
            }
            isMassTooLow() -> {
                MassInput.error = getString(R.string.mass_err_low)
                return false
            }
            else -> return true
        }
    }
    private fun isMassEmpty() =  MassInput.text.isNullOrEmpty()
    private fun isMassTooHigh() =  MassInput.text.toString().toInt() > maxMass
    private fun isMassTooLow() =  MassInput.text.toString().toInt() < minMass

    private fun isHeightValid():Boolean{
        when{
            isHeightEmpty() -> {
                HeightInput.setError(getString(R.string.height_err_empty))
                return false
            }
            isHeightTooHigh() -> {
                HeightInput.setError(getString(R.string.height_err_high))
                return false
            }
            isHeightTooLow() -> {
                HeightInput.setError((getString(R.string.height_err_low)))
                return false
            }
            else -> return true
        }

    }
    private fun isHeightEmpty() = HeightInput.text.isNullOrEmpty()
    private fun isHeightTooHigh() =  HeightInput.text.toString().toInt() > maxHeight
    private fun isHeightTooLow() = HeightInput.text.toString().toInt() < minHeight


    fun countBmi() {

        if (isInputValid()) {
            val mass = MassInput.text!!.toString().toInt()
            val height = HeightInput.text.toString().toInt()
            val logic = when (unit) {
                "metric" -> BmiForKgCm(mass, height)
                "imperial" -> BmiForLbIn(mass, height)
                else -> BmiForKgCm(mass, height)
            }

            val bmi = logic.countBmi()
            BmiResult.text = String.format("%2.2f", bmi)

            when (bmi) {

                in 0.0..16.0 -> {
                    BmiDescripion.setText(R.string.severly_underweight)
                    BmiResult.setTextColor(getColor(R.color.warning))
                    BmiDescripion.setTextColor(getColor(R.color.warning))

                }
                in 16.0..18.5 -> {
                    BmiDescripion.setText(R.string.underweight)
                    BmiResult.setTextColor(getColor(R.color.lapis_lazuli))
                    BmiDescripion.setTextColor(getColor(R.color.lapis_lazuli))
                }
                in 18.5..25.0 -> {
                    BmiDescripion.setText(R.string.normal_weight)
                    BmiResult.setTextColor(getColor(R.color.grynszpan))
                    BmiDescripion.setTextColor(getColor(R.color.grynszpan))
                }
                in 25.0..30.0 -> {
                    BmiDescripion.setText(R.string.overweight)
                    BmiResult.setTextColor(getColor(R.color.roz_pompejanski))
                    BmiDescripion.setTextColor(getColor(R.color.roz_pompejanski))
                }
                in 30.0..35.0 -> {
                    BmiDescripion.setText(R.string.obese1)
                    BmiResult.setTextColor(getColor(R.color.lapis_lazuli))
                    BmiDescripion.setTextColor(getColor(R.color.lapis_lazuli))
                }
                in 35.0..Double.POSITIVE_INFINITY -> {
                    BmiDescripion.setText(R.string.totaly_obese)
                    BmiResult.setTextColor(Color.BLACK)
                    BmiDescripion.setTextColor(Color.BLACK)
                }
            }

            bmiInfoFAB.show()

        }else {
            clearTexts()
            Toast.makeText(this, R.string.input_err, Toast.LENGTH_SHORT)
        }
    }

    private fun openBmiInfo() {
        val infoIntent = Intent(this, BmiInfoActivity::class.java)
        infoIntent.putExtra(bmiResult,BmiResult.text)
        infoIntent.putExtra(bmiCategory,BmiDescripion.text)
        startActivity(infoIntent)
    }

    fun switchUnits() = when (unit) {
        "metric" -> {
            setImperial()
            clearTexts()
        }
        "imperial" -> {
            setMetric()
            clearTexts()
        }
        else -> {
            Toast.makeText(this, "error during switching units", Toast.LENGTH_SHORT)
        }
    }
    private fun setImperial(){
        unit = "imperial"
        MassLabel.setText(R.string.mass_label_im)
        MassInput.setHint(R.string.mass_hint_im)
        HeightLabel.setText(R.string.height_label_im)
        HeightInput.setHint(R.string.height_label_im)
        maxMass = maxImperialMass
        minMass = minImperialMass
        maxHeight = maxImperialHeight
        minHeight = minImperialHeight
    }
    private fun setMetric(){
        unit = "metric"
        MassLabel.setText(R.string.mass_label)
        MassInput.setHint(R.string.mass_hint)
        HeightLabel.setText(R.string.height_label)
        HeightInput.setHint(R.string.height_label)
        maxMass = maxMetricMass
        minMass = minMetricMass
        maxHeight = maxMetricHeight
        minHeight = minMetricHeight
    }
    private fun clearTexts() {
        MassInput.setText("")
        HeightInput.setText("")
        BmiResult.text = ""
        BmiDescripion.text = ""
        bmiInfoFAB.hide()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(massLabel, MassLabel.text)
        outState.putCharSequence(massHint, MassInput.hint)
        outState.putCharSequence(heightLabel, HeightLabel.text)
        outState.putCharSequence(heightHint, MassInput.hint)
        outState.putCharSequence(bmiResult, BmiResult.text)
        outState.putCharSequence(bmiCategory, BmiDescripion.text)
        outState.putInt(resultColor, BmiResult.currentTextColor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        MassLabel.text = savedInstanceState?.getCharSequence(massLabel)
        MassInput.hint = savedInstanceState?.getCharSequence(massHint)
        HeightLabel.text = savedInstanceState?.getCharSequence(heightLabel)
        MassInput.hint = savedInstanceState?.getCharSequence(heightHint)

        BmiResult.text = savedInstanceState?.getString(bmiResult)
        BmiDescripion.text = savedInstanceState?.getString(bmiResult)

        val color = savedInstanceState!!.getInt(resultColor)
        BmiResult.setTextColor(color)
        BmiDescripion.setTextColor(color)
    }

    companion object {
        private var unit = "metric"

        private var maxMass = 300
        private var minMass = 20

        private var maxHeight = 280
        private var minHeight = 100

        private var maxMetricMass = 300
        private var minMetricMass = 20

        private var maxMetricHeight = 280
        private var minMetricHeight = 100

        private var maxImperialMass = 600
        private var minImperialMass = 44

        private var maxImperialHeight = 110
        private var minImperialHeight = 40

        val massLabel = "massLabel"
        val massHint = "massHint"
        val heightLabel = "heightLabel"
        val heightHint = "heightHint"
        val bmiResult = "bmiResult"
        val bmiCategory = "bmiCategory"
        val resultColor = "resultColor"
    }
}

