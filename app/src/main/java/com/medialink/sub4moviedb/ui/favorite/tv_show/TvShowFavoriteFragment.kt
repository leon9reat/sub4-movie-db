package com.medialink.sub4moviedb.ui.favorite.tv_show


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
import com.medialink.sub4moviedb.Consts
import com.medialink.sub4moviedb.R
import com.medialink.sub4moviedb.adapter.favorite.TvShowFavoriteAdapter
import com.medialink.sub4moviedb.database.Favorite
import com.medialink.sub4moviedb.ui.favorite.movie.MovieFavoriteFragmentDirections
import com.medialink.sub4moviedb.ui.popular.tv_show.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFavoriteFragment : Fragment(), TvShowFavoriteAdapter.ItemClickListener {

    private lateinit var tvShowFavoriteViewModel: TvShowFavoriteViewModel
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var adapter: TvShowFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            tvShowFavoriteViewModel = ViewModelProvider(it).get(TvShowFavoriteViewModel::class.java)
            tvShowViewModel = ViewModelProvider(it).get(TvShowViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUi()
    }

    private fun setupViewModel() {
        with(tvShowFavoriteViewModel) {
            favTvShow.observe(viewLifecycleOwner, Observer {
                adapter.update(it)

                val visibility = if (it.size > 0) View.GONE else View.VISIBLE
                layout_empty.visibility = visibility
            })
        }
    }

    private fun setupUi() {
        rv_tvshow_favorite.setHasFixedSize(true)
        val i = resources.configuration.orientation
        rv_tvshow_favorite.layoutManager =
            if (i == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(context)
            else GridLayoutManager(context, 2)
        rv_tvshow_favorite.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        adapter = TvShowFavoriteAdapter(
            tvShowFavoriteViewModel.favTvShow.value ?: emptyList(),
            this
        )
        rv_tvshow_favorite.adapter = adapter
    }

    override fun onItemClicked(tvShow: Favorite) {
        val toFavoriteDetailFragment = TvShowFavoriteFragmentDirections
            .actionTvShowFavoriteFragmentToFavoriteDetailFragment(tvShow)
            .setType(Consts.TV_SHOW_TYPE)
        findNavController().navigate(toFavoriteDetailFragment)
    }

    override fun onDeleteClicked(tvShow: Favorite) {
        tvShowFavoriteViewModel.deleteFavorite(tvShow)
        tvShowViewModel.setChangeData()
    }

}
