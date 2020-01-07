package com.medialink.sub4moviedb.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.medialink.sub4moviedb.R
import com.medialink.sub4moviedb.adapter.popular.PopularPagerAdapter

class PopularFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_popular, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // settin tabLayout
        activity?.let {
            val popularPagerAdapter = PopularPagerAdapter(it, childFragmentManager)
            val popularPager: ViewPager = view.findViewById(R.id.pager_popular)
            popularPager.adapter = popularPagerAdapter

            val tabs: TabLayout = view.findViewById(R.id.tab_popular)
            tabs.setupWithViewPager(popularPager)
            for (i in 0 until popularPagerAdapter.count) {
                tabs.getTabAt(i)?.setIcon(PopularPagerAdapter.TAB_IMAGE[i])
            }
        }

    }
}