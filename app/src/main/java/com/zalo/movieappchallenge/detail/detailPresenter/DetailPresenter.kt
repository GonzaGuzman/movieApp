package com.zalo.movieappchallenge.detail.detailPresenter

import android.content.Intent
import android.content.res.Resources
import com.zalo.movieappchallenge.detail.detailActivity.MOVIE_ID
import com.zalo.movieappchallenge.detail.detailDatasource.DetailDataSource
import com.zalo.movieappchallenge.network.models.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable


class DetailPresenter(
    private val detailView: DetailView,
    private val detailDataSource: DetailDataSource,
    private val resources: Resources,
) : DetailPresenterActions {

    private val compositeDisposable = CompositeDisposable()
    override fun initComponent(intent: Intent) {
        val id = intent.getLongExtra(MOVIE_ID, 0L)
        getMovieLocalDatabase(id)
    }

    override fun getMovieDetail(id: Long) {
        compositeDisposable.add(
            detailDataSource.getMovieDetail(id,
                {
                    detailView.retrieverExtras(it)
                    insertMovie(it)

                }, {
                    detailView.showSnackBar(it.message.toString())
                }
            )
        )
    }

    override fun getMovieLocalDatabase(id: Long) {
        compositeDisposable.add(
            detailDataSource.getMovieLocalDatabase(id,
                {
                    detailView.retrieverExtras(it)

                }, {
                    detailView.showSnackBar(it.message.toString())
                    getMovieDetail(id)
                }
            )
        )
    }

    override fun insertMovie(movie: Movie) {
        compositeDisposable.add(
            detailDataSource.insertMovie(movie,
                { println("Movie save in movileDatabase") },
                { println("Movie is not saved${it.message.toString()}") }
            )
        )
    }

}