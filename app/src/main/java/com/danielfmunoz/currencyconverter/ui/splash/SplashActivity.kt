package com.danielfmunoz.currencyconverter.ui.splash
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danielfmunoz.currencyconverter.R
import com.danielfmunoz.currencyconverter.ui.main.MainActivity
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer = Timer()
        timer.schedule(
            timerTask {
                var intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            2000
        )
    }
}