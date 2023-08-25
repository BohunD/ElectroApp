package com.example.electroapp.presenation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.electroapp.R
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.util.DATA_ADS
import com.example.electroapp.data.util.DATA_AD_USER_ID
import com.example.electroapp.databinding.FragmentHomeBinding
import com.example.electroapp.presenation.adapters.AdvertisementsAdapter
import com.example.electroapp.presenation.listeners.AdListenerImpl
import com.example.electroapp.presenation.viewmodels.AdvertisementViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class HomeFragment : ElectroFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var adapter: AdvertisementsAdapter?=null
    private lateinit var viewModel: AdvertisementViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[AdvertisementViewModel::class.java]
        adapter = AdvertisementsAdapter(arrayListOf(),userId!!)
        listener = AdListenerImpl(binding.rvAdvertisements,this@HomeFragment, adapter!!)
        adapter!!.setListener(listener!!)
        binding.rvAdvertisements.adapter = adapter
        updateList()
    }
    private fun updateList() {
        val ads = arrayListOf<Advertisement>()
        firebaseDB.collection(DATA_ADS).get()
            .addOnSuccessListener { list ->
                for (document in list.documents) {
                    val ad = document.toObject(Advertisement::class.java)
                    ad?.let { ads.add(ad)}
                }
                adapter?.updateAds(ads)
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong... :(", Toast.LENGTH_SHORT
                ).show()            }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}