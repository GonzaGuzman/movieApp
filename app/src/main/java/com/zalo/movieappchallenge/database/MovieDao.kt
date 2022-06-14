package com.zalo.movieappchallenge.database

import androidx.room.*
import com.zalo.movieappchallenge.network.models.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun getMovies(): Single<List<Movie>>

    @Query("SELECT * FROM movies WHERE id =:id")
    fun getById(id: Long): Single<Movie>

    @Update
    fun update(movie: Movie): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie): Completable

    @Delete
    fun delete(movie: Movie): Single<Unit>
}


