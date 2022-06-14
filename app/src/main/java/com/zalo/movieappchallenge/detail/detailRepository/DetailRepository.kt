package com.zalo.movieappchallenge.detail.detailRepository

import com.zalo.movieappchallenge.database.MovieDatabase
import com.zalo.movieappchallenge.network.APIServiceImplements
import com.zalo.movieappchallenge.network.models.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class DetailRepository(private val apiServiceImp: APIServiceImplements, private val database: MovieDatabase) {

    fun getMovieDetail(id: Long): Single<Movie> {
        return apiServiceImp.getMovieDetail(id)
    }

    fun insert(movie: Movie): Completable{
        return database.movieDao().insert(movie)
    }

    fun getMovieLocalDatabase(id: Long): Single<Movie>{
        return database.movieDao().getById(id)
    }
}