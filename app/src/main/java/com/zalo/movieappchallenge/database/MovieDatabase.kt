package com.zalo.movieappchallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zalo.movieappchallenge.network.models.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}
