package com.medialink.sub4moviedb.ui.favorite


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.medialink.sub4moviedb.Consts
import com.medialink.sub4moviedb.R
import com.medialink.sub4moviedb.database.Favorite
import kotlinx.android.synthetic.main.fragment_favorite_detail.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteDetailFragment : Fragment() {

    private lateinit var args: Favorite
    private var type: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        args = FavoriteDetailFragmentArgs.fromBundle(arguments as Bundle).favorite
        type = FavoriteDetailFragmentArgs.fromBundle(arguments as Bundle).type

        return inflater.inflate(R.layout.fragment_favorite_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_title_favorite.text = args.title
        tv_label_date_favorite.text =
            if (type == Consts.MOVIE_TYPE) resources.getString(R.string.label_release) else resources.getString(
                R.string.label_airing
            )
        tv_date_favorite.text = args.date

        img_poster_favorite.load("${Consts.TMDB_PHOTO_URL2}${args.poster}") {
            crossfade(true)
            placeholder(R.drawable.ic_file_download_black_24dp)
        }
        tv_overview_favorite.text = args.overview
    }
}
