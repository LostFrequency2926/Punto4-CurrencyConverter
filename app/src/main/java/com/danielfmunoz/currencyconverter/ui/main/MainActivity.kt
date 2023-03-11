package com.danielfmunoz.currencyconverter.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.danielfmunoz.currencyconverter.R
import com.danielfmunoz.currencyconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val currencies = listOf("COP", "EUR", "USD", "CAD", "CNY", "RUB")
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val resultConvertObserver = Observer<Double> {resultConvert ->
            mainBinding.textViewResultado.text = resultConvert.toString()
        }

        mainViewModel.resultConvert.observe(this, resultConvertObserver)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mainBinding.spinnerOrigen.adapter = adapter
        mainBinding.spinnerDestino.adapter = adapter

        mainBinding.btnConvertir.setOnClickListener {

            if (mainViewModel.realizarValidateNulls(mainBinding.editTextCantidad.text.toString())) {
                mainViewModel.convertCurrency(
                    mainBinding.editTextCantidad.text.toString(),
                    currencies[mainBinding.spinnerOrigen.selectedItemPosition],
                    currencies[mainBinding.spinnerDestino.selectedItemPosition]
                )
            }else{
                Toast.makeText(this, getString(R.string.wrongValue), Toast.LENGTH_SHORT).show()
                mainBinding.textViewResultado.text = ""
            }
        }
    }
}
