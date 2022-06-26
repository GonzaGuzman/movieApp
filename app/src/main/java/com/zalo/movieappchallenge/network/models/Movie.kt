package com.zalo.movieappchallenge.network.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//modelo Movie obtenido de API TMdb
//modelo de tabla utilizada en database Local
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @NonNull
    val numberItem: Int,
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String,
)




