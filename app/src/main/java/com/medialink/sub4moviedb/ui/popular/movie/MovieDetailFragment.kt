package com.medialink.sub4moviedb.ui.popular.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import com.medialink.sub4moviedb.Consts

import com.medialink.sub4moviedb.R
import com.medialink.sub4moviedb.model.movie.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {

    private lateinit var args: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        args = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).movie
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_title_movie.text = args.title
        tv_release_movie.text = args.releaseDate
        tv_vote_movie.text = args.voteAverage?.toString()
        img_poster_movie.load("${Consts.TMDB_PHOTO_URL2}${args.posterPath}") {
            crossfade(true)
            placeholder(R.drawable.ic_file_download_black_24dp)
        }

        progress_vote_movie.progress = args.voteAverage?.times(10)?.toInt() ?: 0
        tv_overview_movie.text = args.overview
    }
}
