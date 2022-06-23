package com.zalo.movieappchallenge.search.searchDatasource

import com.zalo.movieappchallenge.network.models.MoviesResponse
import com.zalo.movieappchallenge.search.searchRepository.SearchRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchDatasourceImplements(private val searchRepository: SearchRepository) :
    SearchDatasource {

    override fun searchMovie(
        query: String,
        onSuccess: (responsive: MoviesResponse) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return searchRepository.searchMovie(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }
}