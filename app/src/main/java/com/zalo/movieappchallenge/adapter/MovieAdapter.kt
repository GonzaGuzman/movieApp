package com.zalo.movieappchallenge.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zalo.movieappchallenge.R
import com.zalo.movieappchallenge.detail.detailActivity.DetailActivity
import com.zalo.movieappchallenge.detail.detailActivity.MOVIE_ID
import com.zalo.movieappchallenge.network.models.Movie


class MovieAdapter(private val movieList: MutableList<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(MOVIE_ID, item.id)
            holder.itemView.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int = movieList.size

    fun appendMovies(movies: List<Movie>) {
        this.movieList.addAll(movies)
        notifyItemRangeInserted(
            this.movieList.size,
            movies.size - 1
        )
    }
}
/*
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }
*/

