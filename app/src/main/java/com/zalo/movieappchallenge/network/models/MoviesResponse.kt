package com.zalo.movieappchallenge.network.models

import com.google.gson.annotations.SerializedName

//modelo de respuesta obtenida de API TMdb
data class MoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)