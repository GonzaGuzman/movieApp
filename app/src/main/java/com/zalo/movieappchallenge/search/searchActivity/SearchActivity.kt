package com.zalo.movieappchallenge.search.searchActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zalo.movieappchallenge.adapter.MovieAdapter
import com.zalo.movieappchallenge.databinding.ActivitySearchBinding
import com.zalo.movieappchallenge.network.APIServiceImplements
import com.zalo.movieappchallenge.network.models.Movie
import com.zalo.movieappchallenge.search.searchDatasource.SearchDatasourceImplements
import com.zalo.movieappchallenge.search.searchPresenter.SearchPresenter
import com.zalo.movieappchallenge.search.searchPresenter.SearchView
import com.zalo.movieappchallenge.search.searchRepository.SearchRepository
import com.zalo.movieappchallenge.util.KEY_SEARCH

class SearchActivity : AppCompatActivity(), SearchView {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private val apiServiceImp = APIServiceImplements
    private val searchRepository = SearchRepository(apiServiceImp)
    private val searchDatasourceImplements = SearchDatasourceImplements(searchRepository)
    private lateinit var searchPresenter: SearchPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchPresenter = SearchPresenter(this, searchDatasourceImplements,resources)
        searchPresenter.searchMovies(intent)
    }

    //carga vista de Recycler
    override fun loadRecycler() {
        recyclerView = binding.recyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        adapter = MovieAdapter(mutableListOf())
        recyclerView.adapter = adapter
    }


    //actualiza la lista de adapter no paginado
    override fun onPopularMoviesFetched(movies: List<Movie>) =
        adapter.appendMoviesSearch(movies)

    // imprime por pantalla mensaje en caso de falla
    override fun showSnackBar(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

    //actualiza intent de a SearchActivity con la nueva busqueda proporcionada
    override fun textSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        with(binding) {
            toolbarSearch.edtSearch.setOnClickListener {
                if (!toolbarSearch.edtSearch.text.isNullOrEmpty()) {
                    intent.putExtra(KEY_SEARCH, toolbarSearch.edtSearch.text.toString())
                    searchPresenter.searchMovies(intent)
                    toolbarSearch.edtSearch.text?.clear()
                }
            }
        }
    }
}