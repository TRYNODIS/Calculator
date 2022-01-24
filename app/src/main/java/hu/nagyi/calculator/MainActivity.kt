package hu.nagyi.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    //region VARIABLES

    lateinit var resultTV: TextView
    private var lastNumeric = false
    private var stateError = false
    private var lastDot = false

    //endregion

    //region METHODS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.resultTV = this.findViewById(R.id.resultTV)
    }

    fun onDigit(view: View) {
        if (this.stateError) {
            this.resultTV.text = (view as Button).text
            this.stateError = false
        } else {
            this.resultTV.append((view as Button).text)
        }
        this.lastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (this.lastNumeric && !this.stateError && !this.lastDot) {
            this.resultTV.append(".")
            this.lastNumeric = false
            this.lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (this.lastNumeric && !this.stateError) {
            this.resultTV.append((view as Button).text)
            this.lastNumeric = false
            this.lastDot = false
        }
    }


    fun onClear(view: View) {
        this.resultTV.text = ""
        this.lastNumeric = false
        this.stateError = false
        this.lastDot = false
    }

    fun onEqual(view: View) {
        if (this.lastNumeric && !this.stateError) {
            val text = this.resultTV.text.toString()
            val expression = ExpressionBuilder(text).build()
            try {
                val result = expression.evaluate()
                this.resultTV.text = result.toString()
                this.lastDot = true
            } catch (ex: Exception) {
                this.resultTV.text = "Error"
                this.stateError = true
                this.lastNumeric = false
            }
        }
    }

    //endregion
}