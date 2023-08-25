package com.example.electroapp.presenation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.type.Fraction

class PhotosViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    fragments: ArrayList<Fragment>,

) : FragmentStatePagerAdapter(fragmentManager) {
    private var currentFragment: Fragment? = null


    private var mFragments = fragments?: arrayListOf<Fragment>()
    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {

        val fragment = mFragments[position]
        currentFragment = fragment
        return fragment
    }
    fun getCurrentFragment(): Fragment? {
        return currentFragment
    }


}