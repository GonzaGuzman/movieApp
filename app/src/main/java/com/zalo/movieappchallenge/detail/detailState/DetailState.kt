package com.zalo.movieappchallenge.detail.detailState

import androidx.databinding.ObservableField

class DetailState {

    val id: ObservableField<Long?> = ObservableField<Long?>(0L)
    val title: ObservableField<String?> = ObservableField<String?>()
    val overview: ObservableField<String?> = ObservableField<String?>()
    val posterPath: ObservableField<String?> = ObservableField<String?>()
    val backdropPath: ObservableField<String?> = ObservableField<String?>()
    val rating: ObservableField<Float?> = ObservableField<Float?>(0f)
    val releaseDate: ObservableField<String?> = ObservableField<String?>()
}
