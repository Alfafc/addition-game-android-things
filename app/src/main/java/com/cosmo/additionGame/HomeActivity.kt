package com.cosmo.additionGame

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random
import kotlin.random.asJavaRandom

class HomeActivity : Activity() {

    private val timer: Timer = Timer()
    private lateinit var currentOperation: Operation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        buttonRight.visibility = android.view.View.INVISIBLE
        buttonMiddle.visibility = android.view.View.INVISIBLE
        buttonLeft.visibility = android.view.View.INVISIBLE

        timer.schedule(showNewOperationTimerTask(), 2000)

        buttonRight.setOnClickListener {
            checkResult(buttonRight)
        }

        buttonMiddle.setOnClickListener {
            checkResult(buttonMiddle)
        }

        buttonLeft.setOnClickListener {
            checkResult(buttonLeft)
        }
    }

    fun showNewOperation() {
        currentOperation = Operation()

        textView.setText("${currentOperation.firstValue} ${currentOperation.operator.sign} ${currentOperation.secondValue} = ?")

        val values = listOf(
            currentOperation.resultValue,
            currentOperation.resultValue + getRandomDelta(),
            currentOperation.resultValue + getRandomDelta()
        )
        val shuffledValues = values.shuffled(Random.asJavaRandom())

        buttonRight.visibility = android.view.View.VISIBLE
        buttonMiddle.visibility = android.view.View.VISIBLE
        buttonLeft.visibility = android.view.View.VISIBLE

        buttonLeft.setText(shuffledValues[0].toString())
        buttonMiddle.setText(shuffledValues[1].toString())
        buttonRight.setText(shuffledValues[2].toString())

        buttonLeft.setEnabled(true)
        buttonMiddle.setEnabled(true)
        buttonRight.setEnabled(true)
    }

    private fun getRandomDelta(): Int {

        if (Random.nextBoolean())
            return Random.nextInt(1, 4);
        return Random.nextInt(-4, -1);
    }

    fun checkResult(clickedButton: Button) {

        val result = clickedButton.text.toString()
        val resultText: String
        if (currentOperation.resultValue.toString().equals(result)) {
            resultText = "Bien es $result!"

            textView.setText("${currentOperation.firstValue} ${currentOperation.operator.sign} ${currentOperation.secondValue} = ${currentOperation.resultValue}")

            timer.schedule(showNewOperationTimerTask(), 2000)
        } else {
            resultText = "MAL no es $result!"
            clickedButton.setEnabled(false)
        }

        Toast.makeText(
            this@HomeActivity,
            resultText,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showNewOperationTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                this@HomeActivity.runOnUiThread {
                    showNewOperation()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        timer.purge()
    }
}
