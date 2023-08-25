package com.example.electroapp.presenation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.electroapp.R
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.databinding.FragmentPhotoVpFitCenterBinding
import com.example.electroapp.databinding.FragmentPhotosVpBinding


class PhotoVpFitCenterFragment : Fragment() {
    private lateinit var binding: FragmentPhotoVpFitCenterBinding

    private var url: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments.let {
            url = arguments?.getString(PhotosVpFragment.IMAGE)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoVpFitCenterBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun init(){
        url?.let {
            binding.ivPhoto.loadUrl(binding.ivPhoto.context,it)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    companion object {
        const val IMAGE = "photo"

        @JvmStatic
        fun newInstance(url: String) : PhotoVpFitCenterFragment{
            val fragment = PhotoVpFitCenterFragment()
            val bundle = Bundle()
            bundle.putString(IMAGE,url )
            fragment.arguments = bundle
            return fragment
        }
    }
}