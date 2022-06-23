package com.zalo.movieappchallenge

import android.content.Intent
import android.content.res.Resources
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.movieappchallenge.detail.detailDatasource.DetailDataSource
import com.zalo.movieappchallenge.detail.detailPresenter.DetailPresenter
import com.zalo.movieappchallenge.detail.detailPresenter.DetailView
import com.zalo.movieappchallenge.network.models.Movie
import io.reactivex.rxjava3.disposables.Disposable
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailPresenterTest {
    @Mock
    private lateinit var detailView: DetailView

    @Mock
    private lateinit var detailDataSource: DetailDataSource

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var mockDisposable: Disposable

    private lateinit var detailPresenter: DetailPresenter

    @Before
    fun setup() {
        detailPresenter = DetailPresenter(detailView, detailDataSource, resources)
    }

    @Test
    fun `initComponent and the flag is true`() {
        //GIVEN
        getMovieLocalDatabaseSuccessfully()
        val intent = Mockito.mock(Intent::class.java)
        //WHEN
        detailPresenter.initComponent(intent)
        //THEN
        verify(detailView).retrieverExtras(MOVIE)
    }

    @Test
    fun `initComponent and flag is false and getMovieDetail success and insertMovie success`() {
        //GIVEN
        getMovieLocalDatabaseUnsuccessfully()
        getMovieDetailSuccessfully()
        insetMovieSuccessfully()
        val intent = Mockito.mock(Intent::class.java)
        //WHEN
        detailPresenter.initComponent(intent)
        //THEN
        verify(detailView).retrieverExtras(MOVIE)
    }

    @Test
    fun `initComponent and flag is false and getMovieDetail success and insertMovie fail`() {
        //GIVEN
        getMovieLocalDatabaseUnsuccessfully()
        getMovieDetailSuccessfully()
        insetMovieUnsuccessfully()
        val intent = Mockito.mock(Intent::class.java)
        //WHEN
        detailPresenter.initComponent(intent)
        //THEN
        verify(detailView).retrieverExtras(MOVIE)
    }


    @Test
    fun `initComponent and flag is false and getMovieDetail is fail`() {
        //GIVEN
        getMovieLocalDatabaseUnsuccessfully()
        getMovieDetailUnsuccessfully()
        val intent = Mockito.mock(Intent::class.java)
        whenever(resources.getString(R.string.error_message)).thenReturn(THIS_FAIL)
        //WHEN
        detailPresenter.initComponent(intent)
        //THEN
        verify(detailView).showSnackBar(THIS_FAIL)
    }

    @Test
    fun `getMovieDetail success and insertMovie success`() {
        //GIVEN
        val id = LONG_1
        getMovieDetailSuccessfully()
        insetMovieSuccessfully()
        //WHEN
        detailPresenter.getMovieDetail(id)
        //THEN
        verify(detailView).retrieverExtras(MOVIE)
    }

    @Test
    fun `getMovieDetail success and insertMovie fail`() {
        //GIVEN
        val id = LONG_1
        getMovieDetailSuccessfully()
        insetMovieUnsuccessfully()
        //WHEN
        detailPresenter.getMovieDetail(id)
        //THEN
        verify(detailView).retrieverExtras(MOVIE)
    }

    @Test
    fun `getMovieDetail fail`() {
        //GIVEN
        val id = LONG_1
        getMovieDetailUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn(THIS_FAIL)
        //WHEN
        detailPresenter.getMovieDetail(id)
        //THEN
        verify(detailView).showSnackBar(THIS_FAIL)
    }


    @Test
    fun `getMovieLocalDatabase success and flag is true`() {
        //GIVEN
        val id = LONG_1
        getMovieLocalDatabaseSuccessfully()
        //WHEN
        val flag = detailPresenter.getMovieLocalDatabase(id)
        //THEN
        verify(detailView).retrieverExtras(MOVIE)
        assertEquals(flag, true)
    }

    @Test
    fun `getMovieLocalDatabase success and id is empty`() {
        //GIVEN
        val id = LONG_1
        getMovieLocalDatabaseSuccessfullyAndEmpty()
        //WHEN
        detailPresenter.getMovieLocalDatabase(id)
        //THEN
        verify(detailView, never()).retrieverExtras(MOVIE)
    }

    @Test
    fun `getMovieLocalDatabase success and id is not empty`() {
        //GIVEN
        val id = LONG_1
        getMovieLocalDatabaseSuccessfully()
        //WHEN
        detailPresenter.getMovieLocalDatabase(id)
        //THEN
        verify(detailView).retrieverExtras(MOVIE)
    }

    @Test
    fun `getMovieLocalDatabase fail`() {
        //GIVEN
        val id = LONG_1
        getMovieLocalDatabaseUnsuccessfully()
        //WHEN
        detailPresenter.getMovieLocalDatabase(id)
        //THEN
        verify(detailView, never()).retrieverExtras(MOVIE)
    }

    @Test
    fun `insertMovie success`() {
        //GIVEN
        insetMovieSuccessfully()
        //WHEN
        detailPresenter.insertMovie(MOVIE)
        //THEN
    }

    @Test
    fun `insertMovie fail`() {
        //GIVEN
        insetMovieUnsuccessfully()
        //WHEN
        detailPresenter.insertMovie(MOVIE)
        //THEN
    }

    @Test
    fun `dataBaseLimit with argument less than limit`() {
        //GIVEN
        val num = ONE
        //WHEN
        detailPresenter.dataBaseLimit(num)
        //THEN
        verify(detailDataSource).setCountItems(TWO)
        assertEquals(detailPresenter.dataBaseLimit(num), ONE)
    }

    @Test
    fun `dataBaseLimit with argument greater than limit`() {
        //GIVEN
        val num = 50
        //WHEN
        detailPresenter.dataBaseLimit(num)
        //THEN
        verify(detailDataSource).setCountItems(ONE)
        assertEquals(detailPresenter.dataBaseLimit(num), ZERO)
    }


    private fun getMovieDetailSuccessfully() {
        val success = argumentCaptor<(Movie) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(detailDataSource.getMovieDetail(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            success.firstValue.invoke(MOVIE)
            mockDisposable
        }
    }

    private fun getMovieDetailUnsuccessfully() {
        val success = argumentCaptor<(Movie) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseError = Mockito.mock(Throwable::class.java)
        whenever(detailDataSource.getMovieDetail(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            error.firstValue.invoke(responseError)
            mockDisposable
        }
    }

    private fun getMovieLocalDatabaseSuccessfullyAndEmpty() {
        val success = argumentCaptor<(Movie) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(detailDataSource.getMovieLocalDatabase(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            success.firstValue.invoke(MOVIE_EMPTY)
            mockDisposable
        }
    }


    private fun getMovieLocalDatabaseSuccessfully() {
        val success = argumentCaptor<(Movie) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(detailDataSource.getMovieLocalDatabase(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            success.firstValue.invoke(MOVIE)
            mockDisposable
        }
    }

    private fun getMovieLocalDatabaseUnsuccessfully() {
        val success = argumentCaptor<(Movie) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseError = Mockito.mock(Throwable::class.java)
        whenever(detailDataSource.getMovieLocalDatabase(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            error.firstValue.invoke(responseError)
            mockDisposable
        }
    }

    private fun insetMovieSuccessfully() {
        val success = argumentCaptor<() -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(detailDataSource.insertMovie(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            success.firstValue.invoke()
            mockDisposable
        }
    }

    private fun insetMovieUnsuccessfully() {
        val success = argumentCaptor<() -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseError = Mockito.mock(Throwable::class.java)
        whenever(detailDataSource.insertMovie(
            any(),
            success.capture(),
            error.capture()
        )).thenAnswer {
            error.firstValue.invoke(responseError)
            mockDisposable
        }
    }

    companion object {
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
        const val LONG_1 = 1L
        const val THIS_FAIL = "UPS ALGO SALIO MAL"
        private val MOVIE = Movie(1, 1, "movieTest", "", "", "", 0F, "")
        private val MOVIE_EMPTY = Movie(0, 0, "", "", "", "", 0F, "")


    }
}