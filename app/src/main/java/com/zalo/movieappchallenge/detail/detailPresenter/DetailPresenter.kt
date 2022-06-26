package com.zalo.movieappchallenge.detail.detailPresenter

import android.content.Intent
import android.content.res.Resources
import android.util.Log
import com.zalo.movieappchallenge.R
import com.zalo.movieappchallenge.detail.detailDatasource.DetailDataSource
import com.zalo.movieappchallenge.network.models.Movie
import com.zalo.movieappchallenge.util.LIMIT_DATABASE
import com.zalo.movieappchallenge.util.MOVIE_ID
import io.reactivex.rxjava3.disposables.CompositeDisposable

const val TAG = "DetailPresenter"

class DetailPresenter(
    private val detailView: DetailView,
    private val detailDataSource: DetailDataSource,
    private val resources: Resources,
) : DetailPresenterActions {

    private val compositeDisposable = CompositeDisposable()
    override fun initComponent(intent: Intent) {
        val id = intent.getLongExtra(MOVIE_ID, 0L)
        if (!getMovieLocalDatabase(id)) {
            getMovieDetail(id)
        }
    }

    //busca una movie en API TMdb a partir de su id
    override fun getMovieDetail(id: Long) {
        compositeDisposable.add(
            detailDataSource.getMovieDetail(id,
                {
                    detailView.retrieverExtras(it)
                    insertMovie(it)
                }, {
                    detailView.showSnackBar(resources.getString(R.string.error_message))
                    Log.e(TAG, it.message.toString())
                }
            )
        )
    }

    //busca una movie en database local a partir de su id
    override fun getMovieLocalDatabase(id: Long): Boolean {
        var flag = false
        compositeDisposable.add(
            detailDataSource.getMovieLocalDatabase(id,
                {
                    flag = if (it.id == (0L)) {
                        false
                    } else {
                        detailView.retrieverExtras(it)
                        true
                    }
                },
                { Log.e(TAG, it.message.toString()) }
            )
        )
        return flag
    }

    //inserta una nueva movie en database local
    override fun insertMovie(movie: Movie) {
        val numberItem = dataBaseLimit(detailDataSource.getCountItems())
        val movieDb = movie.copy(numberItem = numberItem)
        compositeDisposable.add(
            detailDataSource.insertMovie(movieDb,
                { Log.i(TAG, resources.getString(R.string.movie_saved)) },
                { Log.e(TAG, it.message.toString()) }
            )
        )
    }

    //setea el valor del contador de items gardados en database de acuerdo al limite de items a gardadar establecido
    override fun dataBaseLimit(num: Int): Int {
        var numberReturn = num
        if (num >= LIMIT_DATABASE) {
            numberReturn = 0
            detailDataSource.setCountItems(1)
        } else {
            detailDataSource.setCountItems(num + 1)
        }
        return numberReturn
    }

}