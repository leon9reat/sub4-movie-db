package com.medialink.sub4moviedb.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.medialink.sub4moviedb.R
import com.medialink.sub4moviedb.adapter.favorite.FavoritePagerAdapter
import com.medialink.sub4moviedb.adapter.popular.PopularPagerAdapter

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val favoritePagerAdapter = FavoritePagerAdapter(it, childFragmentManager)
            val favoritePager: ViewPager = view.findViewById(R.id.pager_favorite)
            favoritePager.adapter = favoritePagerAdapter

            val tabs: TabLayout = view.findViewById(R.id.tab_favorite)
            tabs.setupWithViewPager(favoritePager)
            for (i in 0 until favoritePagerAdapter.count) {
                tabs.getTabAt(i)?.setIcon(PopularPagerAdapter.TAB_IMAGE[i])
            }
        }
    }
}