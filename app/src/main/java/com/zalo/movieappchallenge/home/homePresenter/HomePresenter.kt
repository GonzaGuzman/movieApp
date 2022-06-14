package com.zalo.movieappchallenge.home.homePresenter

import android.content.res.Resources
import com.zalo.movieappchallenge.home.homeDataSource.HomeDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable


class HomePresenter(
    private val homeView: HomeView,
    private val homeDatasource: HomeDataSource,
    private val resources: Resources,
) : HomePresenterActions {
    private val compositeDisposable = CompositeDisposable()

    override fun initComponent(page: Int) {
        homeView.getMoviesByPage(page)
        homeView.loadRecycler()
        homeView.textSearch()
    }

    override fun getMovies(page: Int) {
        compositeDisposable.add(
            homeDatasource.getListMovies(
                page,
                {
                    homeView.onPopularMoviesFetched(it.movies)
                }, { error -> homeView.showSnackBar(error.message.toString()) }
            )
        )
    }


}