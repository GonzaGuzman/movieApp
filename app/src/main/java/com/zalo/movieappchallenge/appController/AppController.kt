package com.zalo.movieappchallenge.appController

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.zalo.movieappchallenge.database.MovieDatabase
import com.zalo.movieappchallenge.util.MOVIE_DATABASE

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences("tmdbPreferences", 0)

        database =
            Room.databaseBuilder(this, MovieDatabase::class.java, MOVIE_DATABASE).build()
    }

    companion object {
        lateinit var database: MovieDatabase
        lateinit var preferences: SharedPreferences
    }
}

