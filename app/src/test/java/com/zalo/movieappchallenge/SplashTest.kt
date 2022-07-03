package com.zalo.movieappchallenge

import com.nhaarman.mockito_kotlin.verify
import com.zalo.movieappchallenge.splash.splashPresenter.SplashPresenter
import com.zalo.movieappchallenge.splash.splashPresenter.SplashView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashTest {
    @Mock
    private lateinit var splashView: SplashView

    private lateinit var splashPresenter: SplashPresenter

    @Before
   fun setup(){
        splashPresenter = SplashPresenter(splashView)
    }

    @Test
    fun `call initComponent`(){
        //GIVEN
        //WHEN
        splashPresenter.initComponent()
        //THEN
        verify(splashView).show()
    }

    @Test
    fun `call navigateTo`(){
        //GIVEN
        //WHEN
        splashPresenter.navigateTo()
        //THEN
        verify(splashView).startHome()
    }
}