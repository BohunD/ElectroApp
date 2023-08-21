package com.example.electroapp.presenation.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
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
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
        user?.let {
            startActivity(HomeActivity.newIntent(this))
        }
    }
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

    }

    private fun initViews() = with(binding) {
        tabs = this.tabLayout
        sectionPageAdapter = SectionPageAdapter(this@HomeActivity as FragmentActivity, fragmentList)
        viewPager.adapter = sectionPageAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.icon = ContextCompat.getDrawable(this@HomeActivity, iconList[position])
            tab.text = tabTextList[position]
            tab.view.setOnClickListener {
                if (position == 2)
                    userId?.let {
                        startActivity(NewAdActivity.newIntent(this@HomeActivity, userId!!))
                    }
            }
        }.attach()


    }

    override fun onResume() {
        super.onResume()
        tabs.getTabAt(0)?.select()
    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}