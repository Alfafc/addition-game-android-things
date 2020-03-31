package com.cosmo.additionGame

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : Activity() {

    private val timer: Timer = Timer()
    private lateinit var currentOperation: Operation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

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

        buttonLeft.setText(currentOperation.resultValue.toString())
        buttonMiddle.setText(currentOperation.firstValue.toString())
        buttonRight.setText(currentOperation.secondValue.toString())

        buttonLeft.setEnabled(true)
        buttonMiddle.setEnabled(true)
        buttonRight.setEnabled(true)
    }

    fun checkResult(clickedButton: Button) {

        val result = clickedButton.text.toString()
        val resultText: String
        if (currentOperation.resultValue.toString().equals(result)) {
            resultText = "Bien es $result!"

            timer.schedule(showNewOperationTimerTask(), 2000)
        } else {
            resultText = "MAL no es $result!"
            clickedButton.setEnabled(false)
        }

        Toast.makeText(
            this@MainActivity,
            resultText,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showNewOperationTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                this@MainActivity.runOnUiThread {
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
