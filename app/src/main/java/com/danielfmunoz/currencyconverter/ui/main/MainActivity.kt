package com.danielfmunoz.currencyconverter.ui.main
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.danielfmunoz.currencyconverter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val currencies = listOf("COP", "EUR", "USD", "CAD", "CNY", "RUB")
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mainBinding.spinnerOrigen.adapter = adapter
        mainBinding.spinnerDestino.adapter = adapter

        mainBinding.btnConvertir.setOnClickListener {
            val cantidadStr = mainBinding.editTextCantidad.text.toString()
            if (cantidadStr.isBlank()) {
                mainBinding.textViewResultado.text = ""
                mainBinding.editTextCantidad.error = "Ingresa una cantidad válida"
                return@setOnClickListener
            }

            val cantidad = cantidadStr.toDouble()
            val origen = currencies[mainBinding.spinnerOrigen.selectedItemPosition]
            val destino = currencies[mainBinding.spinnerDestino.selectedItemPosition]

            val result = CurrencyConverter.convert(cantidad, origen, destino)

            mainBinding.textViewResultado.text = "$cantidad $origen = $result $destino"
            mainBinding.editTextCantidad.error = null
        }
    }

    private object CurrencyConverter {
        private val rates = mapOf(
            "COP" to mapOf(
                "COP" to 1.0, "EUR" to 0.00019, "USD" to 0.00021, "CAD" to 0.00028, "CNY" to 0.0014, "RUB" to 0.015
            ),
            "EUR" to mapOf(
                "COP" to 5173.43, "EUR" to 1.0, "USD" to 1.07, "CAD" to 1.45, "CNY" to 7.33, "RUB" to 88.30
            ),
            "USD" to mapOf(
                "COP" to 4848.28, "EUR" to 0.94, "USD" to 1.0, "CAD" to 1.36, "CNY" to 6.87, "RUB" to 75.27
            ),
            "CAD" to mapOf(
                "COP" to 3567.36, "EUR" to 0.69, "USD" to 0.74, "CAD" to 1.0, "CNY" to 5.05, "RUB" to 55.38
            ),
            "CNY" to mapOf(
                "COP" to 705.81, "EUR" to 0.14, "USD" to 0.15, "CAD" to 0.20, "CNY" to 1.0, "RUB" to 10.96
            ),
            "RUB" to mapOf(
                "COP" to 64.42, "EUR" to 0.012, "USD" to 0.013, "CAD" to 0.018, "CNY" to 0.091, "RUB" to 1.0
            )
        )

        fun convert(cantidad: Double, origen: String, destino: String): Double {
            val rate = rates[origen]?.get(destino) ?: throw IllegalArgumentException("Tasas de cambio no encontradas para las monedas especificadas")
            return cantidad * rate
        }
    }
}

