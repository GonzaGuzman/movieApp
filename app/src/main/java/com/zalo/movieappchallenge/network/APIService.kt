package com.zalo.movieappchallenge.network

import com.zalo.movieappchallenge.BuildConfig.API_KEY
import com.zalo.movieappchallenge.BuildConfig.BASE_URL
import com.zalo.movieappchallenge.network.models.Movie
import com.zalo.movieappchallenge.network.models.MoviesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): Single<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
    ): Single<Movie>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Single<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): Single<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): Single<MoviesResponse>
}

object APIServiceImplements {
    fun getPopularMovies(page: Int): Single<MoviesResponse> {
        return service.getPopularMovies(API_KEY, page)
    }

    fun searchMovie(query: String): Single<MoviesResponse> {
        return service.searchMovie(API_KEY, query,1)
    }

    fun getTopRatedMovies(page: Int): Single<MoviesResponse> {
        return service.getTopRatedMovies(API_KEY, page)
    }

    fun getUpcomingMovies(page: Int): Single<MoviesResponse> {
        return service.getUpcomingMovies(API_KEY, page)
    }

    fun getMovieDetail(id: Long): Single<Movie> {
        return service.getMovieDetail(id, API_KEY)
    }

    private val service: APIService by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}
