package com.example.bmi

import android.content.Intent
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
            item?.title == getString(R.string.abm_about_me) -> {
                val aboutMeIntent = Intent(this, AboutMeActivity::class.java)
                startActivity(aboutMeIntent)
            }
            item?.title == getString(R.string.bmi_switch_units) -> {
                switchUnits()
            }
            item?.title == getString(R.string.his_history) -> {
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
                MassInputET.error = getString(R.string.bmi_mass_err_empty)
                return false
            }
            isMassTooHigh() -> {
                MassInputET.error = getString(R.string.bmi_mass_err_high)
                return false
            }
            isMassTooLow() -> {
                MassInputET.error = getString(R.string.bmi_mass_err_low)
                return false
            }
            else -> return true
        }
    }
    private fun isMassEmpty() =  MassInputET.text.isNullOrEmpty()
    private fun isMassTooHigh() =  MassInputET.text.toString().toInt() > maxMass
    private fun isMassTooLow() =  MassInputET.text.toString().toInt() < minMass

    private fun isHeightValid():Boolean{
        when{
            isHeightEmpty() -> {
                HeightInputET.setError(getString(R.string.bmi_height_err_empty))
                return false
            }
            isHeightTooHigh() -> {
                HeightInputET.setError(getString(R.string.bmi_height_err_high))
                return false
            }
            isHeightTooLow() -> {
                HeightInputET.setError((getString(R.string.bmi_height_err_low)))
                return false
            }
            else -> return true
        }

    }
    private fun isHeightEmpty() = HeightInputET.text.isNullOrEmpty()
    private fun isHeightTooHigh() =  HeightInputET.text.toString().toInt() > maxHeight
    private fun isHeightTooLow() = HeightInputET.text.toString().toInt() < minHeight


    fun countBmi() {

        if (isInputValid()) {
            val mass = MassInputET.text!!.toString().toInt()
            val height = HeightInputET.text.toString().toInt()
            val logic = when (unit) {
                "metric" -> BmiForKgCm(mass, height)
                "imperial" -> BmiForLbIn(mass, height)
                else -> BmiForKgCm(mass, height)
            }

            val bmi = logic.countBmi()
            BmiResultTV.text = String.format("%2.2f", bmi)

            when (bmi) {

                in 0.0..16.0 -> {
                    BmiCategoryTV.setText(R.string.severely_underweight)
                    BmiResultTV.setTextColor(getColor(R.color.roz_pompejanski))
                    BmiCategoryTV.setTextColor(getColor(R.color.roz_pompejanski))

                }
                in 16.0..18.5 -> {
                    BmiCategoryTV.setText(R.string.underweight)
                    BmiResultTV.setTextColor(getColor(R.color.lapis_lazuli))
                    BmiCategoryTV.setTextColor(getColor(R.color.lapis_lazuli))
                }
                in 18.5..25.0 -> {
                    BmiCategoryTV.setText(R.string.normal_weight)
                    BmiResultTV.setTextColor(getColor(R.color.grynszpan))
                    BmiCategoryTV.setTextColor(getColor(R.color.grynszpan))
                }
                in 25.0..30.0 -> {
                    BmiCategoryTV.setText(R.string.overweight)
                    BmiResultTV.setTextColor(getColor(R.color.roz_pompejanski))
                    BmiCategoryTV.setTextColor(getColor(R.color.roz_pompejanski))
                }
                in 30.0..35.0 -> {
                    BmiCategoryTV.setText(R.string.obese1)
                    BmiResultTV.setTextColor(getColor(R.color.lapis_lazuli))
                    BmiCategoryTV.setTextColor(getColor(R.color.lapis_lazuli))
                }
                in 35.0..Double.POSITIVE_INFINITY -> {
                    BmiCategoryTV.setText(R.string.totaly_obese)
                    BmiResultTV.setTextColor(getColor(R.color.kasztanowaty))
                    BmiCategoryTV.setTextColor(getColor(R.color.kasztanowaty))
                }
            }

            bmiInfoFAB.show()

        }else {
            clearTexts()
            Toast.makeText(this, R.string.bmi_input_err, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openBmiInfo() {
        val infoIntent = Intent(this, BmiInfoActivity::class.java)
        infoIntent.putExtra(bmiResult,BmiResultTV.text)
        infoIntent.putExtra(bmiCategory,BmiCategoryTV.text)
        infoIntent.putExtra(resultColor,BmiResultTV.currentTextColor)
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
            Toast.makeText(this, "error during switching units", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setImperial(){
        unit = "imperial"
        MassLabelTV.setText(R.string.bmi_mass_label_lb)
        MassInputET.setHint(R.string.bmi_mass_hint_lb)
        HeightLabelTV.setText(R.string.bmi_height_label_in)
        HeightInputET.setHint(R.string.bmi_height_label_in)
        maxMass = maxImperialMass
        minMass = minImperialMass
        maxHeight = maxImperialHeight
        minHeight = minImperialHeight
    }
    private fun setMetric(){
        unit = "metric"
        MassLabelTV.setText(R.string.bmi_mass_label_kg)
        MassInputET.setHint(R.string.bmi_mass_hint_kg)
        HeightLabelTV.setText(R.string.bmi_height_label_cm)
        HeightInputET.setHint(R.string.bmi_height_label_cm)
        maxMass = maxMetricMass
        minMass = minMetricMass
        maxHeight = maxMetricHeight
        minHeight = minMetricHeight
    }
    private fun clearTexts() {
        MassInputET.setText("")
        HeightInputET.setText("")
        BmiResultTV.text = ""
        BmiCategoryTV.text = ""
        bmiInfoFAB.hide()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(massLabel, MassLabelTV.text)
        outState.putCharSequence(massHint, MassInputET.hint)
        outState.putCharSequence(heightLabel, HeightLabelTV.text)
        outState.putCharSequence(heightHint, MassInputET.hint)
        outState.putCharSequence(bmiResult, BmiResultTV.text)
        outState.putCharSequence(bmiCategory, BmiCategoryTV.text)
        outState.putInt(resultColor, BmiResultTV.currentTextColor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        MassLabelTV.text = savedInstanceState?.getCharSequence(massLabel)
        MassInputET.hint = savedInstanceState?.getCharSequence(massHint)
        HeightLabelTV.text = savedInstanceState?.getCharSequence(heightLabel)
        MassInputET.hint = savedInstanceState?.getCharSequence(heightHint)

        BmiResultTV.text = savedInstanceState?.getString(bmiResult)
        BmiCategoryTV.text = savedInstanceState?.getString(bmiResult)

        val color = savedInstanceState!!.getInt(resultColor)
        BmiResultTV.setTextColor(color)
        BmiCategoryTV.setTextColor(color)
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

