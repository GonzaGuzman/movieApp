package com.zalo.movieappchallenge.splash.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zalo.movieappchallenge.databinding.ActivitySplashBinding
import com.zalo.movieappchallenge.home.homeActivity.HomeActivity
import com.zalo.movieappchallenge.splash.splashPresenter.SplashPresenter
import com.zalo.movieappchallenge.splash.splashPresenter.SplashView
import java.util.*

//Activity que muestra logo de TMdb al iniciar la app
class SplashActivity : AppCompatActivity(), SplashView {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var splashPresenter: SplashPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashPresenter = SplashPresenter(this)
        splashPresenter.initComponent()
    }

    override fun show() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                splashPresenter.navigateTo()
            }
        }, SPLASH_DELAY)

    }

    override fun startHome() =
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java)).apply {
            finish()
        }

    companion object {
        private const val SPLASH_DELAY = 2_000L
    }
}
