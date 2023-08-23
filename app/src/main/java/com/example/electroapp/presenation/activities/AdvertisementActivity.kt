package com.example.electroapp.presenation.activities

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.electroapp.R
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.models.User
import com.example.electroapp.data.util.DATA_USERS
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.databinding.ActivityAdvertisementBinding
import com.example.electroapp.databinding.ActivityNewAdBinding
import com.example.electroapp.presenation.adapters.AdFilterTvAdapter
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
        if(intent.hasExtra(EXTRA_AD)){
            val ad = intent.getParcelableExtra<Advertisement>(EXTRA_AD)
            ad?.let{
                binding.tvName.text = ad.name
                binding.tvPrice.text = ad.price
                binding.ivPhoto.loadUrl(binding.ivPhoto.context, ad.photos?.get(0))
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
            }

        }
    }

    companion object{
        const val EXTRA_AD = "ad"
        fun newIntent(context: Context, ad: Advertisement): Intent{
            val intent = Intent(context, AdvertisementActivity::class.java)
            intent.putExtra(EXTRA_AD, ad)
            return intent
        }
    }
}