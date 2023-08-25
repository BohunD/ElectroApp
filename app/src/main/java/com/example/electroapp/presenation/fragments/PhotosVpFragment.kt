package com.example.electroapp.presenation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.electroapp.R
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.databinding.FragmentPhotosVpBinding

class PhotosVpFragment : Fragment() {

    private lateinit var binding: FragmentPhotosVpBinding
    private var iv: ImageView?=null
    private var list: ArrayList<String>?=null
    private var url: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            url = arguments?.getString(IMAGE)
        }

    }

    private fun init(){
        url?.let {
            binding.ivPhoto.loadUrl(binding.ivPhoto.context,it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosVpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }
    fun setScaleType(scaleType: ImageView.ScaleType) {
        this.binding.ivPhoto.scaleType = scaleType
    }

    companion object {
        const val IMAGE = "photo"

        @JvmStatic
        fun newInstance(url: String) : PhotosVpFragment{
            val fragment = PhotosVpFragment()
            val bundle = Bundle()
            bundle.putString(IMAGE,url )
            fragment.arguments = bundle
            return fragment
        }
    }
}