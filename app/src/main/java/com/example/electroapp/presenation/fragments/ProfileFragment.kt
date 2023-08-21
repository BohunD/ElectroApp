package com.example.electroapp.presenation.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.example.electroapp.R
import com.example.electroapp.data.models.User
import com.example.electroapp.data.util.DATA_IMAGES
import com.example.electroapp.data.util.DATA_USERS
import com.example.electroapp.data.util.DATA_USER_IMAGE_URL
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.databinding.FragmentProfileBinding
import com.example.electroapp.presenation.activities.StartActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class ProfileFragment : ElectroFragment() {

    private lateinit var binding : FragmentProfileBinding
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            storeImage(data?.data)
        }
    }
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var imageUrl: String?=null
    private var userName: String?=null
    private var userPhone: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        with(binding){
            ivUserPhoto.setOnClickListener {  launchPhotoLoading() }
            btnSignout.setOnClickListener {
                firebaseAuth.signOut()
                requireActivity().startActivity(StartActivity.newIntent(requireContext()))
                requireActivity().finish()
            }
        }
    }

    private fun launchPhotoLoading(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun initViews() = with(binding){

        firebaseDB.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                imageUrl = user?.imageUrl
                userName = user?.username
                userPhone = user?.phoneNumber
                imageUrl?.let{url ->
                    ivUserPhoto.loadUrl(url)
                }
                userName?.let{name ->
                    tvName.text = name
                }
                userPhone?.let{phone ->
                    tvPhone.text = phone
                }

            }

    }

    private fun storeImage(imageUri: Uri?){
        imageUri?.let {
            Toast.makeText(requireContext(), "Uploading...", Toast.LENGTH_SHORT).show()
            binding.profileProgressLayout.visibility = View.VISIBLE
            val filePath = firebaseStorage.child(DATA_IMAGES).child(userId!!)
            filePath.putFile(imageUri)
                .addOnSuccessListener {
                    filePath.downloadUrl.addOnSuccessListener {
                        var url = it.toString()
                        firebaseDB.collection(DATA_USERS).document(userId)
                            .update(DATA_USER_IMAGE_URL, url)
                            .addOnSuccessListener {
                                imageUrl = url
                                binding.ivUserPhoto.loadUrl(imageUrl, R.drawable.error_image)
                            }
                    }.addOnFailureListener {
                        onUploadFailure()
                    }
                    binding.profileProgressLayout.visibility = View.GONE
                }.addOnFailureListener {
                    onUploadFailure()
                }
        }

    }

    private fun onUploadFailure(){
        Toast.makeText(
            requireContext(),
            "Upload failed. Try again later", Toast.LENGTH_SHORT
        ).show()
        binding.profileProgressLayout.visibility = View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProfileFragment()

    }
}