package com.example.electroapp.presenation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.electroapp.R
import com.example.electroapp.databinding.FragmentPhotosVpFullScreenBinding
import com.example.electroapp.presenation.adapters.PhotosViewPagerAdapter


class PhotosVpFullScreenFragment : Fragment() {

    private var photos: ArrayList<String>?=null
    private var position: Int?=null
    private lateinit var binding: FragmentPhotosVpFullScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosVpFullScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragments = arrayListOf<Fragment>()

        arguments?.let {
            photos = it.getStringArrayList(ARG_PARAM1)
            position = it.getInt(ARG_PARAM2)
            for (photo in photos!!) {
                val fragment = PhotoVpFitCenterFragment.newInstance(photo)
                fragments.add(fragment)
            }
            val photosVpAdapter = PhotosViewPagerAdapter(childFragmentManager, fragments)
            binding.photosViewPager.adapter = photosVpAdapter

            binding.photosTabLayout.setupWithViewPager(binding.photosViewPager, true)



            binding.photosViewPager.setCurrentItem(position!!, false)
        }
    }

    companion object {

        const val ARG_PARAM1 = "param1"
        const val ARG_PARAM2 = "param2"
        @JvmStatic
        fun newInstance(param1: ArrayList<String>, param2: Int) =
            PhotosVpFullScreenFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)

                }
            }
    }
}