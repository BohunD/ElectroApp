package com.example.electroapp.presenation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.electroapp.R
import com.example.electroapp.databinding.FragmentPhotoVpFitCenterBinding
import com.example.electroapp.databinding.FragmentSellerAdsBinding


class SellerAdsFragment : Fragment() {

    private lateinit var sellerID: String

    private lateinit var binding: FragmentSellerAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSellerAdsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SellerAdsFragment()
    }
}