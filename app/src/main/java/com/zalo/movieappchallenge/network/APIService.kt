package com.zalo.movieappchallenge.network

import com.zalo.movieappchallenge.network.models.Movie
import com.zalo.movieappchallenge.network.models.MoviesResponse
import com.zalo.movieappchallenge.util.API_KEY
import com.zalo.movieappchallenge.util.BASE_URL
import io.reactivex.rxjava3.core.Single
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
        @Query("language") language: String?,
        @Query("page") page: Int,
    ): Single<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
    ): Single<Movie>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Single<MoviesResponse>

}

object APIServiceImplements {
    fun getPopularMovies(page: Int): Single<MoviesResponse> = service.getPopularMovies(API_KEY,"es", page)

    fun searchMovie(query: String): Single<MoviesResponse> = service.searchMovie(API_KEY,"es", query, 1)

    fun getMovieDetail(id: Long): Single<Movie> = service.getMovieDetail(id, API_KEY,"es")

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
