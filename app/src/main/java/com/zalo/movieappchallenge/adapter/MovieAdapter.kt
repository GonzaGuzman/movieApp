package com.zalo.movieappchallenge.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zalo.movieappchallenge.R
import com.zalo.movieappchallenge.detail.detailActivity.DetailActivity
import com.zalo.movieappchallenge.network.models.Movie
import com.zalo.movieappchallenge.util.MOVIE_ID
class MovieAdapter(private val movieList: MutableList<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(MOVIE_ID, item.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movieList.size

    //metodo actualiza movieList cuando cambia el tamaño de esta al cargar una nueva pagina
    fun appendMovies(movies: List<Movie>) {
        this.movieList.addAll(movies)
        notifyItemRangeInserted(
            this.movieList.size,
            movies.size - 1
        )
    }

    //metodo que actualiza movieList cuando no se utiliza paginacion
    fun appendMoviesSearch(movies: List<Movie>) {
        this.movieList.addAll(movies)
        notifyDataSetChanged()
    }
}

