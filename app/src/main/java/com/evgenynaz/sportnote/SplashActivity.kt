package com.evgenynaz.sportnote

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.evgenynaz.sportnote.databinding.ActivityBmiBinding
import com.evgenynaz.sportnote.databinding.SplashLayoutBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashLayoutBinding
    private val SPLASH_DISPLAY_LENGHT = 3000 //set your time here......
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@SplashActivity, ActivityStart::class.java)
            this@SplashActivity.startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGHT.toLong())
    }
}