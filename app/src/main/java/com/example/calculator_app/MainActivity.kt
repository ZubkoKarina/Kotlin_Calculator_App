package com.example.calculator_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator_app.databinding.ActivityMainBinding
import java.lang.ArithmeticException
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.Expression
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNymeric = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun onEqualClick(view: View) {
        OnEqual()
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)
    }
    fun onDigitClick(view: View) {
        if(stateError) {
            binding.dataTv.text = (view as Button).text
            stateError = false
        }else {
            binding.dataTv.append((view as Button).text)
        }
        lastNymeric = true
        OnEqual()
    }
    fun onAllClearClick(view: View) {
        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastDot = false
        lastNymeric = false
        binding.resultTv.visibility = View.GONE
    }
    fun onOperatorClick(view: View) {
        if(!stateError && lastNymeric){
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNymeric = false
            OnEqual()

        }
    }
    fun onBackClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try{
            val lastChar = binding.dataTv.text.toString().last()
            if(lastChar.isDigit()){
                OnEqual()
            }
        }catch (e: Exception){
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }
    fun onCliarClick(view: View) {
        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastDot = false
        lastNymeric = false
        binding.resultTv.visibility = View.GONE
    }
    fun OnEqual(){
        if(lastNymeric && !stateError){
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()
            try{
                val result = expression.evaluate()
                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()
            }catch (ex: ArithmeticException){
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastNymeric = false
            }
        }
    }
}