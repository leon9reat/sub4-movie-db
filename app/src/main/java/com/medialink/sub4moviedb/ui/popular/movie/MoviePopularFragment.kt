package com.medialink.sub4moviedb.ui.popular.movie


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medialink.sub4moviedb.R
import com.medialink.sub4moviedb.adapter.popular.MovieAdapter
import com.medialink.sub4moviedb.model.movie.Movie
import kotlinx.android.synthetic.main.fragment_movie_popular.*
import kotlinx.android.synthetic.main.layout_error.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MoviePopularFragment : Fragment(), MovieAdapter.ItemClickListener {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            movieViewModel = ViewModelProvider(it).get(MovieViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUi()

        if (!movieViewModel.localLanguage.equals(Locale.getDefault().country, ignoreCase = true)) {
            movieViewModel.loadMovies(1)
            movieViewModel.localLanguage = Locale.getDefault().country
        }
    }

    private fun setupViewModel() {
        with(movieViewModel) {
            movies.observe(viewLifecycleOwner,
                Observer<List<Movie>> {
                    layout_error.visibility = View.GONE
                    layout_empty.visibility = View.GONE
                    adapter.update(it)
                })

            isViewLoading.observe(viewLifecycleOwner, Observer<Boolean> {
                val visibility = if (it) View.VISIBLE else View.GONE
                progress_movie.visibility = visibility
            })

            isEmptyList.observe(viewLifecycleOwner, Observer<Boolean> {
                val visibility = if (it) View.VISIBLE else View.GONE
                layout_empty.visibility = visibility
            })

            onMessageError.observe(viewLifecycleOwner, Observer {
                layout_empty.visibility = View.GONE
                layout_error.visibility = View.VISIBLE
                tv_error.text = "Error $it"
            })

            isDataChanged.observe(viewLifecycleOwner, Observer {
                if (it) {
                    movieViewModel.loadMovies(1)
                }
            })
        }
    }

    private fun setupUi() {
        rv_movie.setHasFixedSize(true)
        val i = resources.configuration.orientation
        if (i == Configuration.ORIENTATION_PORTRAIT) {
            rv_movie.layoutManager = LinearLayoutManager(context)
            rv_movie.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        } else {
            rv_movie.layoutManager = GridLayoutManager(context, 2)
            rv_movie.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        adapter = MovieAdapter(
            movieViewModel.movies.value ?: emptyList(),
            this
        )

        rv_movie.adapter = adapter
    }

    override fun onItemClicked(movie: Movie, position: Int) {
       val toMovieDetailFragment = MoviePopularFragmentDirections
           .actionMoviePopularFragmentToMovieDetailFragment(movie)
        findNavController().navigate(toMovieDetailFragment)
    }

    override fun onLikeClicked(movie: Movie, position: Int) {
        movieViewModel.updateMovieFavorite(movie, position)
        adapter.notifyItemChanged(position)
    }

    override fun onShareClicked(movie: Movie) {
        Log.d("debug", "onShareClicked :${movie.title}")
    }

}
