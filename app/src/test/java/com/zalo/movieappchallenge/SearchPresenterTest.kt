package com.zalo.movieappchallenge

import android.content.Intent
import android.content.res.Resources
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.movieappchallenge.network.models.MoviesResponse
import com.zalo.movieappchallenge.search.searchDatasource.SearchDatasource
import com.zalo.movieappchallenge.search.searchPresenter.SearchPresenter
import com.zalo.movieappchallenge.search.searchPresenter.SearchView
import io.reactivex.rxjava3.disposables.Disposable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTest {
    @Mock
    private lateinit var searchView: SearchView

    @Mock
    private lateinit var searchDataSource: SearchDatasource

    @Mock
    private lateinit var mockDisposable: Disposable

    private lateinit var searchPresenter: SearchPresenter

    @Before
    fun setup() {
        searchPresenter = SearchPresenter(searchView, searchDataSource)
    }

    @Test
    fun `searchMovies success`() {
        //GIVEN
        val intent = Mockito.mock(Intent::class.java)
        searchMovieSuccessfully()
        //WHEN
        searchPresenter.searchMovies(intent)
        //THEN
        verify(searchView).loadRecycler()
        verify(searchView).onPopularMoviesFetched(any())
        verify(searchView).textSearch()
    }

    @Test
    fun `searchMovies fail`() {
        //GIVEN
        val intent = Mockito.mock(Intent::class.java)
        searchMovieUnsuccessfully()
        //WHEN
        searchPresenter.searchMovies(intent)
        //THEN
        verify(searchView, never()).loadRecycler()
        verify(searchView, never()).onPopularMoviesFetched(any())
        verify(searchView).textSearch()
    }

    private fun searchMovieSuccessfully() {
        val success = argumentCaptor<(MoviesResponse) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseMovies = Mockito.mock(MoviesResponse::class.java)
        whenever(searchDataSource.searchMovie(
            any(),
            success.capture(),
            error.capture(),
        )).thenAnswer {
            success.firstValue.invoke(responseMovies)
            mockDisposable
        }
    }

    private fun searchMovieUnsuccessfully() {
        val success = argumentCaptor<(MoviesResponse) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseError = Mockito.mock(Throwable::class.java)
        whenever(searchDataSource.searchMovie(
            any(),
            success.capture(),
            error.capture(),
        )).thenAnswer {
            error.firstValue.invoke(responseError)
            mockDisposable
        }
    }

}