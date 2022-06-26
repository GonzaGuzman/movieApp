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
import com.zalo.movieappchallenge.home.homeDataSource.HomeDataSourceImplements
import com.zalo.movieappchallenge.home.homePresenter.HomePresenter
import com.zalo.movieappchallenge.home.homePresenter.HomeView
import com.zalo.movieappchallenge.home.homeRepository.HomeRepository
import com.zalo.movieappchallenge.network.APIServiceImplements
import com.zalo.movieappchallenge.network.models.Movie
import com.zalo.movieappchallenge.search.searchActivity.SearchActivity
import com.zalo.movieappchallenge.util.KEY_SEARCH

//Activity inicio, que contiene un toolbar para busquedas y un recycler para mostrar el poster de una lista de Movies
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

    // activa/desactiva progressBar y llama a metodo getMovies
    override fun getMoviesByPage(page: Int) {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
        homePresenter.getMovies(page)
        isLoading = false
        binding.progressBar.visibility = View.GONE
    }

    //carga vista de Recycler
    override fun loadRecycler() {
        recyclerView = binding.recyclerView
        binding.recyclerView.layoutManager = gridLayoutManager
        adapter = MovieAdapter(mutableListOf())
        recyclerView.adapter = adapter
    }

    //actualiza la lista de adapter paginado
    override fun onPopularMoviesFetched(movies: List<Movie>) {
        adapter.appendMovies(movies)
        attachMoviesOnScrollListener()
    }

    //controla los items que se muestran por recycler y actualiza la lista con una nueva pagina
    // en caso de haber mostrado más de la mitad de los items que contiene la lista
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


    // imprime por pantalla mensaje en caso de falla
    override fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    //envia a SearchActivity la busqueda proporcionada
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

    //columnas de GridLayout
    companion object {
        const val GRID_COLUMNS = 3
    }
}
