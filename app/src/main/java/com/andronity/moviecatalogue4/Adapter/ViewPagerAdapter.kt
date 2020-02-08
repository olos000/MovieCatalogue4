package com.andronity.moviecatalogue4.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.andronity.moviecatalogue4.Fragment.MovieFavoritFragment
import com.andronity.moviecatalogue4.Fragment.TvShowFavoritFragment


class ViewPagerAdapter ( fm: FragmentManager) : FragmentPagerAdapter(fm) {
    // sebuah list yang menampung objek Fragment
    private val pages = listOf(
        MovieFavoritFragment(),
        TvShowFavoritFragment()

    )

    // menentukan fragment yang akan dibuka pada posisi tertentu
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }



    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Movie "
            else -> "TvShow "
        }
    }
}