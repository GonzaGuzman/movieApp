package com.zalo.movieappchallenge.home.homeDataSource

import com.zalo.movieappchallenge.home.homeRepository.HomeRepository
import com.zalo.movieappchallenge.network.models.MoviesResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeDataSourceImplements(private val homeRepository: HomeRepository) : HomeDataSource {
    //obtiene de API TMdb el listado de PopularMovies de la pagina indicada
    override fun getListMovies(
        page: Int,
        onSuccess: (responsive: MoviesResponse) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return homeRepository.getPopularMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }
}