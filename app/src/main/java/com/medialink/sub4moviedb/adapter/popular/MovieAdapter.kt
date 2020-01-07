package com.medialink.sub4moviedb.adapter.popular

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.medialink.sub4moviedb.Consts
import com.medialink.sub4moviedb.R
import com.medialink.sub4moviedb.model.movie.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(
    private var movies: List<Movie>,
    private var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(movie: Movie, position: Int)
        fun onLikeClicked(movie: Movie, position: Int)
        fun onShareClicked(movie: Movie)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, position: Int) {
            with(itemView) {
                tv_title_movie.text = movie.title
                tv_date_movie.text = movie.releaseDate
                tv_overview_movie.text = movie.overview
                img_movie_poster.load("${Consts.TMDB_PHOTO_URL}${movie.posterPath}") {
                    crossfade(true)
                    placeholder(R.drawable.ic_file_download_black_24dp)
                    transformations(RoundedCornersTransformation(8F, 8F, 8F, 8F))
                }
                tv_vote_movie.text = movie.voteAverage.toString()
                progress_vote_movie.progress = movie.voteAverage?.times(10)?.toInt() ?: 0

                btn_like.setOnClickListener { itemClickListener.onLikeClicked(movie, position) }
                btn_share.setOnClickListener { itemClickListener.onShareClicked(movie) }
                this.setOnClickListener { itemClickListener.onItemClicked(movie, position) }

                btn_like.backgroundTintList = if (movie.isFavorite)
                    ColorStateList.valueOf(resources.getColor(android.R.color.holo_red_light))
                    else ColorStateList.valueOf(resources.getColor(R.color.colorAccent))

                Log.d("debug", "${movie.title} = ${movie.isFavorite}")
            }
        }
    }

    fun update(data: List<Movie>) {
        if (data.isNotEmpty()) {
            this.movies = data
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(movies[position], position)
    }
}