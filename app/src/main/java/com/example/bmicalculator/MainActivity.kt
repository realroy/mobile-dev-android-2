package com.example.bmicalculator

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException


enum class BMIType(val text: String) {
    UNDERWEIGHT("Underweight"),
    OKAY("Okay"),
    FAT("Fat")
}

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculateButton.setOnClickListener { view -> onClickCalculateButton(view) }
    }

    private fun onClickCalculateButton(view: View?) {

        val d = 0.toDouble()
        val height: Double = try { heightEditText.text.toString().toDouble() } catch (e: NumberFormatException) {
            d
        }
        val weight: Double = try { weightEditText.text.toString().toDouble() } catch (e: NumberFormatException) {
            d
        }
        val bmi = calcBMI(height, weight)
        val bmiType = getBMIType(bmi)
        val imageID = getBMIImage(bmiType)

        bmiTextView.text = String.format("Your BMI is %.2f (%s)", bmi, bmiType.text)
        imageView.setImageResource(imageID)

        bmiTextView.visibility = View.VISIBLE
        imageView.visibility = View.VISIBLE

        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun getBMIImage(bmiType: BMIType): Int {
        return when (bmiType) {
            BMIType.FAT -> R.drawable.extremely_obese
            BMIType.OKAY -> R.drawable.normal
            BMIType.UNDERWEIGHT -> R.drawable.underweight
        }
    }

    private fun getBMIType(bmi: Double): BMIType {
        return when {
            bmi > 30 -> BMIType.FAT
            bmi in 22.0..30.0 -> BMIType.OKAY
            else -> BMIType.UNDERWEIGHT
        }
    }

    private fun calcBMI(h: Double, w: Double): Double {
        return w/(h*h)
    }
}
