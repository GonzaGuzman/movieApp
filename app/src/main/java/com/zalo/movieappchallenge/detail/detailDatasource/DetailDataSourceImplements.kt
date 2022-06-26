package com.zalo.movieappchallenge.detail.detailDatasource

import com.zalo.movieappchallenge.detail.detailRepository.DetailRepository
import com.zalo.movieappchallenge.network.models.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailDataSourceImplements(private val detailRepository: DetailRepository) :
    DetailDataSource {

    //busca una movie en API TMdb a partir de su id
    override fun getMovieDetail(
        id: Long,
        onSuccess: (responsive: Movie) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return detailRepository.getMovieDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }

    //busca una movie en database local a partir de su id
    override fun getMovieLocalDatabase(
        id: Long,
        onSuccess: (responsive: Movie) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return detailRepository.getMovieLocalDatabase(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }

    //inserta una nueva movie en database local
    override fun insertMovie(
        movie: Movie,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return detailRepository.insert(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess() },
                { onError(it) })
    }

    //actualiza el valor del contador de numeros de item que se van guardando en database local
    override fun setCountItems(itemNumber: Int) {
       detailRepository.setCountItems(itemNumber)
    }

    //obtiene el valor del contador de numeros de item que se van guardando en database local
    override fun getCountItems(): Int {
        return detailRepository.getCountItems()
    }
}