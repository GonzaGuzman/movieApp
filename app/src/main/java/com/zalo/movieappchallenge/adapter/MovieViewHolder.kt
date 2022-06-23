package com.zalo.movieappchallenge.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zalo.movieappchallenge.util.ImageURLBuilder
import com.zalo.movieappchallenge.R
import com.zalo.movieappchallenge.databinding.ItemMovieBinding
import com.zalo.movieappchallenge.network.models.Movie

class MovieViewHolder(movieView: View) : RecyclerView.ViewHolder(movieView) {
    private val binding = ItemMovieBinding.bind(movieView)

    fun bind(movie: Movie) {
        Glide.with(binding.ivMoviePoster.context)
            .load(ImageURLBuilder.getUrl(movie.posterPath))
            .placeholder(R.drawable.splash_background)
            .error(R.drawable.ic_splash_tmdb)
            .into(binding.ivMoviePoster)
    }
}
