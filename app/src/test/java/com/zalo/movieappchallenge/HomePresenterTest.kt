package com.zalo.movieappchallenge

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.movieappchallenge.home.homeDataSource.HomeDataSource
import com.zalo.movieappchallenge.home.homePresenter.HomePresenter
import com.zalo.movieappchallenge.home.homePresenter.HomeView
import com.zalo.movieappchallenge.network.models.Movie
import com.zalo.movieappchallenge.network.models.MoviesResponse
import io.reactivex.rxjava3.disposables.Disposable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {
    @Mock
    private lateinit var homeView: HomeView

    @Mock
    private lateinit var homeDataSource: HomeDataSource

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var mockDisposable: Disposable

    private lateinit var homePresenter: HomePresenter

    @Before
    fun setup() {
        homePresenter = HomePresenter(homeView, homeDataSource, resources)
    }

    @Test
    fun `initComonent and getListMovies success`() {
        //GIVEN
        val page = ONE
        getListMoviesSuccessfully()
        //WHEN
        homePresenter.initComponent(page)
        //THEN
        verify(homeView).getMoviesByPage(page)
        verify(homeView).loadRecycler()
        verify(homeView).textSearch()
    }

@Test
    fun `initComonent and getListMovies fail`() {
        //GIVEN
        val page = ONE
        getListMoviesUnsuccessfully()
        //WHEN
        homePresenter.initComponent(page)
        //THEN
        verify(homeView).getMoviesByPage(page)
        verify(homeView).loadRecycler()
        verify(homeView).textSearch()
    }

    @Test
    fun `getMovies success`() {
        //GIVEN
        val page = ONE
        getListMoviesSuccessfully()
        //WHEN
        homePresenter.getMovies(page)

        //THEN
        verify(homeView).onPopularMoviesFetched(LIST_OF_MOVIES)
    }

    @Test
    fun `getMovies fail`() {
        //GIVEN
        val page = ONE
        getListMoviesUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn(THIS_FAIL)
        homePresenter.getMovies(page)

        //THEN
        verify(homeView).showSnackBar(THIS_FAIL)
    }

    private fun getListMoviesSuccessfully() {
        val success = argumentCaptor<(MoviesResponse) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val movieList = Mockito.mock(MoviesResponse::class.java)
        whenever(movieList.movies).thenReturn(LIST_OF_MOVIES)
        whenever(homeDataSource.getListMovies(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            success.firstValue.invoke(movieList)
            mockDisposable
        }
    }

    private fun getListMoviesUnsuccessfully() {
        val success = argumentCaptor<(MoviesResponse) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseError = Mockito.mock(Throwable::class.java)
        whenever(homeDataSource.getListMovies(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            error.firstValue.invoke(responseError)
            mockDisposable
        }
    }

    companion object {
        const val ONE = 1
        const val THIS_FAIL = "UPS ALGO SALIO MAL"
        private val MOVIE_1 = Movie(1,1, "movieTest1", "", "", "", 0F, "")
        private val MOVIE_2 = Movie(2, 2,"movieTest2", "", "", "", 0F, "")
        private val MOVIE_3 = Movie(3, 3,"movieTest3", "", "", "", 0F, "")
        private val LIST_OF_MOVIES = listOf(MOVIE_1, MOVIE_2, MOVIE_3)
    }
}
