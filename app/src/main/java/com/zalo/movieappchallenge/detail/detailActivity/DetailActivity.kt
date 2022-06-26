package com.zalo.movieappchallenge.detail.detailActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.snackbar.Snackbar
import com.zalo.movieappchallenge.util.ImageURLBuilder
import com.zalo.movieappchallenge.R
import com.zalo.movieappchallenge.appController.AppController
import com.zalo.movieappchallenge.databinding.ActivityDetailBinding
import com.zalo.movieappchallenge.detail.detailDatasource.DetailDataSourceImplements
import com.zalo.movieappchallenge.detail.detailPresenter.DetailPresenter
import com.zalo.movieappchallenge.detail.detailPresenter.DetailView
import com.zalo.movieappchallenge.detail.detailRepository.DetailRepository
import com.zalo.movieappchallenge.network.APIServiceImplements
import com.zalo.movieappchallenge.network.models.Movie

//Activity encargado de mostrar detalles de una Movie
class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var binding: ActivityDetailBinding
    private val apiServiceImp = APIServiceImplements
    private val movieDatabase = AppController.database
    private val detailRepository = DetailRepository(apiServiceImp, movieDatabase)
    private val detailDataSourceImplements = DetailDataSourceImplements(detailRepository)
    private lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailPresenter = DetailPresenter(this, detailDataSourceImplements, resources)
        detailPresenter.initComponent(intent)
    }
// muestra por pantalla los atributos de movie
    override fun retrieverExtras(movie: Movie) {
        Glide.with(this)
            .load(ImageURLBuilder.getUrl(movie.backdropPath))
            .transform(CenterCrop())
            .placeholder(R.drawable.splash_background)
            .error(R.drawable.ic_splash_tmdb)
            .into(binding.movieBackdrop)

        Glide.with(this)
            .load(ImageURLBuilder.getUrl(movie.posterPath))
            .transform(CenterCrop())
            .placeholder(R.drawable.splash_background)
            .error(R.drawable.ic_splash_tmdb)
            .into(binding.moviePoster)

        binding.movieTitle.text = movie.title
        binding.movieRating.rating = movie.rating / 2
        binding.movieReleaseDate.text = movie.releaseDate
        binding.movieOverview.text = movie.overview
    }

    // imprime por pantalla mensaje en caso de falla
    override fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}