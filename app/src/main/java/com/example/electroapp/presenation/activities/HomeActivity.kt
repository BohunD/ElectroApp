package com.example.electroapp.presenation.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.electroapp.R
import com.example.electroapp.databinding.ActivityHomeBinding
import com.example.electroapp.presenation.adapters.SectionPageAdapter
import com.example.electroapp.presenation.fragments.ElectroFragment
import com.example.electroapp.presenation.fragments.FavoritesFragment
import com.example.electroapp.presenation.fragments.FollowedUsersFragment
import com.example.electroapp.presenation.fragments.HomeFragment
import com.example.electroapp.presenation.fragments.NewAdvertisementFragment
import com.example.electroapp.presenation.fragments.ProfileFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var tabs : TabLayout

    private val firebaseAuth = FirebaseAuth.getInstance()

    private var userId = FirebaseAuth.getInstance().currentUser?.uid

    private val fragmentList = listOf<Fragment>(
        HomeFragment.newInstance(),
        FollowedUsersFragment.newInstance(), NewAdvertisementFragment.newInstance(),
        FavoritesFragment.newInstance(), ProfileFragment.newInstance()
    )

    private var sectionPageAdapter: SectionPageAdapter? = null
    private val iconList = listOf(
        R.drawable.home, R.drawable.followed, R.drawable.new_ad,
        R.drawable.like_active, R.drawable.user
    )
    private val tabTextList = listOf(
        "home", "followed", "new ad", "liked", "profile"
    )
    private val customPageChangeCallback = CustomPageChangeCallback(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

    }

    @SuppressLint("InflateParams")
    private fun initViews() = with(binding) {
        tabs = this.tabLayout
        sectionPageAdapter = SectionPageAdapter(this@HomeActivity as FragmentActivity, fragmentList)
        viewPager.adapter = sectionPageAdapter

        viewPager.registerOnPageChangeCallback(customPageChangeCallback)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            if (position != 2) {
                tab.icon = ContextCompat.getDrawable(this@HomeActivity, iconList[position])
                tab.text = tabTextList[position]
            } else {
                val customView = layoutInflater.inflate(R.layout.custom_tab_view, null)
                val tabTitle = customView.findViewById<TextView>(R.id.tabTitle)
                tabTitle.text = tabTextList[position]
                tab.customView = customView
            }
        }.attach()


    }

    class CustomPageChangeCallback(private val activity: HomeActivity) : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 2) {
                activity.startActivity(NewAdActivity.newIntent(activity, activity.userId!!))
                activity.finish()
            }
        }
    }
    override fun onStart() {
        super.onStart()

    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}