package com.zalo.movieappchallenge.splash.splashPresenter

class SplashPresenter(private val splashView: SplashView) : SplashPresenterActions {

    override fun initComponent() =
        splashView.show()

    override fun navigateTo() =
        splashView.startHome()
}