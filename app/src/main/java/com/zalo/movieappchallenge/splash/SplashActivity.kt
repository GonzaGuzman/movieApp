package com.zalo.movieappchallenge.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zalo.movieappchallenge.databinding.ActivitySplashBinding
import com.zalo.movieappchallenge.home.homeActivity.HomeActivity
import java.util.*

//Activity que muestra logo de TMdb al iniciar la app
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }
        }, SPLASH_DELAY)
    }

    companion object {
        private const val SPLASH_DELAY = 2_000L
    }
}