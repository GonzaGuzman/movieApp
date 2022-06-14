package com.zalo.movieappchallenge.movieApplication

import android.app.Application
import androidx.room.Room
import com.zalo.movieappchallenge.database.MovieDatabase

const val MOVIE_DATABASE = "movieDB"

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
       database =
           Room.databaseBuilder(this, MovieDatabase::class.java, MOVIE_DATABASE).build()
    }

    companion object {
        lateinit var database: MovieDatabase
    }
}

