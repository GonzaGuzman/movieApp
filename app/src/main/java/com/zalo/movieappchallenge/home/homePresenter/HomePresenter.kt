package com.zalo.movieappchallenge.home.homePresenter

import android.content.res.Resources
import android.util.Log
import com.zalo.movieappchallenge.R
import com.zalo.movieappchallenge.home.homeDataSource.HomeDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

const val TAG = "HomePresenter"

class HomePresenter(
    private val homeView: HomeView,
    private val homeDatasource: HomeDataSource,
    private val resources: Resources,
) : HomePresenterActions {
    private val compositeDisposable = CompositeDisposable()

    //inicia componentes de vista
    override fun initComponent(page: Int) {
        homeView.getMoviesByPage(page)
        homeView.loadRecycler()
        homeView.textSearch()
    }

    //obtiene de API TMdb el listado de PopularMovies de la pagina indicada
    override fun getMovies(page: Int) {
        compositeDisposable.add(
            homeDatasource.getListMovies(
                page,
                { homeView.onPopularMoviesFetched(it.movies) },
                {
                    homeView.showSnackBar(resources.getString(R.string.error_message))
                    Log.e(TAG, it.message.toString())
                }
            )
        )
    }
}