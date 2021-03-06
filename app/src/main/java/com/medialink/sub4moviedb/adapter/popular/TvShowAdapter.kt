package com.medialink.sub4moviedb.adapter.popular

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.medialink.sub4moviedb.Consts
import com.medialink.sub4moviedb.R
import com.medialink.sub4moviedb.model.tv_show.TvShow
import kotlinx.android.synthetic.main.tv_show_item.view.*

class TvShowAdapter(
    private var tvShows: List<TvShow>,
    private var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<TvShowAdapter.MyViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(tvShow: TvShow, position: Int)
        fun onLikeClicked(tvShow: TvShow, position: Int)
        fun onShareClicked(tvShow: TvShow)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShow, position: Int) {
            with(itemView) {
                tv_title_tvshow.text = tvShow.name
                tv_first_air_tvshow.text = "First Airing: ${tvShow.firstAirDate}"
                tv_overview_tvshow.text = tvShow.overview
                img_tvshow_poster.load("${Consts.TMDB_PHOTO_URL}${tvShow.posterPath}") {
                    crossfade(true)
                    placeholder(R.drawable.ic_file_download_black_24dp)
                    transformations(RoundedCornersTransformation(8F, 8F, 8F, 8F))
                }

                tv_vote_tvshow.text = tvShow.voteAverage.toString()
                progress_vote_tvshow.progress = tvShow.voteAverage?.times(10)?.toInt() ?: 0

                btn_like_tvshow.setOnClickListener {
                    itemClickListener.onLikeClicked(
                        tvShow,
                        position
                    )
                }
                btn_share_tvshow.setOnClickListener { itemClickListener.onShareClicked(tvShow) }
                this.setOnClickListener { itemClickListener.onItemClicked(tvShow, position) }

                btn_like_tvshow.backgroundTintList = if (tvShow.isFavorite)
                    ColorStateList.valueOf(resources.getColor(android.R.color.holo_red_light))
                else ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
            }
        }
    }

    fun update(data: List<TvShow>) {
        if (data.isNotEmpty()) {
            this.tvShows = data
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tv_show_item, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(tvShows[position], position)
    }
}