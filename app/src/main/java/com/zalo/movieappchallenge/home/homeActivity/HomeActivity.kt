package com.zalo.movieappchallenge.home.homeActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zalo.movieappchallenge.adapter.MovieAdapter
import com.zalo.movieappchallenge.databinding.ActivityMainBinding
import com.zalo.movieappchallenge.detail.detailActivity.KEY_SEARCH
import com.zalo.movieappchallenge.home.homeDataSource.HomeDataSourceImplements
import com.zalo.movieappchallenge.home.homePresenter.HomePresenter
import com.zalo.movieappchallenge.home.homePresenter.HomeView
import com.zalo.movieappchallenge.home.homeRepository.HomeRepository
import com.zalo.movieappchallenge.network.APIServiceImplements
import com.zalo.movieappchallenge.network.models.Movie
import com.zalo.movieappchallenge.search.searchActivity.SearchActivity


class HomeActivity : AppCompatActivity(), HomeView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    private val apiServiceImp = APIServiceImplements
    private val homeRepository = HomeRepository(apiServiceImp)
    private val homeDataSourceImplements = HomeDataSourceImplements(homeRepository)
    private lateinit var homePresenter: HomePresenter

    private var page = 1
    private var isLoading = false
    private var gridLayoutManager = GridLayoutManager(this, GRID_COLUMNS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homePresenter = HomePresenter(this, homeDataSourceImplements, resources)
        homePresenter.initComponent(page)
    }

    override fun getMoviesByPage(page: Int) {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
        homePresenter.getMovies(page)
        isLoading = false
        binding.progressBar.visibility = View.GONE
    }

    override fun loadRecycler() {
        recyclerView = binding.recyclerView
        binding.recyclerView.layoutManager = gridLayoutManager
        adapter = MovieAdapter(mutableListOf())
        recyclerView.adapter = adapter
    }

    override fun onPopularMoviesFetched(movies: List<Movie>) {
        adapter.appendMovies(movies)
        attachMoviesOnScrollListener()
    }

    override fun attachMoviesOnScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = gridLayoutManager.itemCount
                val visibleItemCount = gridLayoutManager.childCount
                val firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()

                if (!isLoading) {
                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                        binding.recyclerView.removeOnScrollListener(this)
                        page++
                        getMoviesByPage(page)
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            }
        })
    }


    override fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun textSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        with(binding) {
            toolbarSearch.edtSearch.setOnClickListener {
                if (!toolbarSearch.edtSearch.text.isNullOrEmpty()) {
                    intent.putExtra(KEY_SEARCH, toolbarSearch.edtSearch.text.toString())
                    startActivity(intent)
                    toolbarSearch.edtSearch.text?.clear()
                }
            }
        }
    }

    companion object {
        const val GRID_COLUMNS = 3
    }
}
