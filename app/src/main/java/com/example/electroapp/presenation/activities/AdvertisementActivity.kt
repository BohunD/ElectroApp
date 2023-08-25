package com.example.electroapp.presenation.activities

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.electroapp.R
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.models.User
import com.example.electroapp.data.util.ClickableViewPager
import com.example.electroapp.data.util.DATA_USERS
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.databinding.ActivityAdvertisementBinding
import com.example.electroapp.databinding.ActivityNewAdBinding
import com.example.electroapp.presenation.adapters.AdFilterTvAdapter
import com.example.electroapp.presenation.adapters.PhotosViewPagerAdapter
import com.example.electroapp.presenation.fragments.PhotosVpFragment
import com.example.electroapp.presenation.fragments.PhotosVpFullScreenFragment
import com.example.electroapp.presenation.fragments.SellerAdsFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore

class AdvertisementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdvertisementBinding
    private lateinit var adapter: AdFilterTvAdapter
    private val firebaseDB = FirebaseFirestore.getInstance()

    private var isPhoneShown = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvertisementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }


    private fun initViews(){
        val fragments = arrayListOf<Fragment>()
        if(intent.hasExtra(EXTRA_AD)){
            val ad = intent.getParcelableExtra<Advertisement>(EXTRA_AD)
            ad?.let{
                binding.tvName.text = ad.name
                binding.tvPrice.text = ad.price
                for(photo in ad.photos!!){
                    val fragment = PhotosVpFragment.newInstance(photo)
                    fragment.view
                    fragments.add(fragment)
                }
                val photosVpAdapter = PhotosViewPagerAdapter(supportFragmentManager, fragments)
                binding.photosViewPager.adapter = photosVpAdapter
                binding.photosTabLayout.setupWithViewPager(binding.photosViewPager, true)
                binding.photosViewPager.setOnItemClickListener(object: ClickableViewPager.OnItemClickListener{
                    override fun onItemClick(position: Int) {
                            // Открывайте PhotosVpFullScreenFragment здесь
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.main_container, PhotosVpFullScreenFragment
                                        .newInstance(ad.photos as ArrayList<String>, position)
                                ).addToBackStack("photos_full_screen").commit()
                        }

                })

                binding.tvDescription.text = ad.description
                adapter = AdFilterTvAdapter(ad.filters!!)
                binding.rvFilters.adapter= adapter
                firebaseDB.collection(DATA_USERS).document(ad.userId!!).get()
                    .addOnSuccessListener {
                        val user = it.toObject(User::class.java)
                        binding.sellerName.text = user?.username
                        binding.ivUserPhoto.loadUrl(binding.ivUserPhoto.context, user?.imageUrl)
                        binding.tvPhoneNumber.setOnClickListener {
                            if(isPhoneShown)
                                binding.tvPhoneNumber.text = user?.phoneNumber
                            else binding.tvPhoneNumber.text = getString(R.string.phone_number)

                            isPhoneShown = !isPhoneShown

                        }
                    }.addOnFailureListener {
                        it.printStackTrace()
                    }
                binding.sellerAds.setOnClickListener {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container,
                        SellerAdsFragment.newInstance(ad))
                        .addToBackStack("sellers_ads").commit() }
            }
        }

    }

    companion object{
        const val EXTRA_AD = "ad"
        const val CENTER_CROP_MODE = "center_crop"

        fun newIntent(context: Context, ad: Advertisement): Intent{
            val intent = Intent(context, AdvertisementActivity::class.java)
            intent.putExtra(EXTRA_AD, ad)
            return intent
        }
    }
}