package com.zalo.movieappchallenge.search.searchPresenter

import android.content.Intent
import com.zalo.movieappchallenge.detail.detailActivity.KEY_SEARCH
import com.zalo.movieappchallenge.search.searchDatasource.SearchDatasourceImplements
import io.reactivex.rxjava3.disposables.CompositeDisposable


class SearchPresenter(
    private val searchView: SearchView,
    private val searchDatasourceImplements: SearchDatasourceImplements,
) : SearchPresenterActions {
    private val composite = CompositeDisposable()


    override fun searchMovies(intent: Intent) {
        val search = intent.getStringExtra(KEY_SEARCH)
        if (!search.isNullOrEmpty()) {
            composite.add(
                searchDatasourceImplements.searchMovie(search,
                    { searchView.loadRecycler()
                        searchView.onPopularMoviesFetched(it.movies)
                    },
                    { searchView.showSnackBar(it.message.toString())})
            )
        }else{
            searchView.showSnackBar("EL CAMPO DE BUSQUEDA SE ECUENTRA VACIO, POR FAVOR INGRESE BUSQUEDA")
        }
        searchView.textSearch()
    }


}