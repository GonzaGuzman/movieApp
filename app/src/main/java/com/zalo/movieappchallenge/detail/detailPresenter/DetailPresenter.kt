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
         if (!getMovieLocalDatabase(id)){
             getMovieDetail(id)
         }
    }

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

    override fun insertMovie(movie: Movie) {
        movie.numberItem = dataBaseLimit(detailDataSource.getCountItems())
        compositeDisposable.add(
            detailDataSource.insertMovie(movie,
                { Log.i(TAG, resources.getString(R.string.movie_saved)) },
                { Log.e(TAG, it.message.toString()) }
            )
        )
    }

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