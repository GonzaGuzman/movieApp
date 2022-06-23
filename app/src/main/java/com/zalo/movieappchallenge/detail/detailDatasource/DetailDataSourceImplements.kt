package com.zalo.movieappchallenge.detail.detailDatasource

import com.zalo.movieappchallenge.detail.detailRepository.DetailRepository
import com.zalo.movieappchallenge.network.models.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailDataSourceImplements(private val detailRepository: DetailRepository) :
    DetailDataSource {

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

    override fun setCountItems(itemNumber: Int) {
       detailRepository.setCountItems(itemNumber)
    }

    override fun getCountItems(): Int {
        return detailRepository.getCountItems()
    }
}