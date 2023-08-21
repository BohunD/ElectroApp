package com.example.electroapp.presenation.activities

import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electroapp.R
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.models.Category
import com.example.electroapp.data.models.User
import com.example.electroapp.data.util.CATEGORIES
import com.example.electroapp.data.util.DATA_ADS
import com.example.electroapp.data.util.PARAM_USER_ID
import com.example.electroapp.data.util.PARAM_USER_NAME
import com.example.electroapp.databinding.ActivityNewAdBinding
import com.example.electroapp.presenation.adapters.AdFilterAdapter
import com.example.electroapp.presenation.adapters.AdPhotosAdapter
import com.example.electroapp.presenation.viewmodels.NewAdViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class NewAdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewAdBinding
    private lateinit var viewModel: NewAdViewModel
    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                loadImages(data)
            }
        }
    private lateinit var adapter: AdPhotosAdapter
    private lateinit var filtersAdapter: AdFilterAdapter
    private val firebaseDB = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(NewAdViewModel::class.java)
        viewModel.init()
        adapter = AdPhotosAdapter(viewModel, arrayListOf(), resultLauncher)
        binding.rvPhotos.adapter = adapter
        viewModel.photoListLiveData.observe(this) {
            adapter = AdPhotosAdapter(viewModel, it, resultLauncher)
            binding.rvPhotos.adapter = adapter
        }
        initSpinner()
        initAdFilterAdapter()
        binding.btnPost.setOnClickListener {
            postAdvertisement()
        }
    }

    private fun initAdFilterAdapter() {
        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        binding.spinnerCategory.background = ContextCompat.getDrawable(
                            this@NewAdActivity, R.drawable.spinner_background
                        )
                        viewModel.updateSelectedCategoryAndFilters(position)
                        viewModel.selectedCategory.observe(this@NewAdActivity) { selectedCategory ->
                            selectedCategory?.let {
                                val selectedFilters = it.filters
                                selectedFilters?.let {
                                    filtersAdapter = AdFilterAdapter(it, viewModel)
                                    binding.rvFilter.adapter = filtersAdapter
                                }
                            }
                        }
                    } else {
                        viewModel.selectedCategory.value = null
                        viewModel.selectedFilters.value = null
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun initSpinner() {
        val categoryNames = listOf("Select") + CATEGORIES.map { it.name }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categoryNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
        binding.spinnerCategory.setSelection(0)
    }

    private fun loadImages(data: Intent?) {
        if (data?.clipData != null) {
            val count = data.clipData!!.itemCount
            for (i in 0 until count) {
                val imageUrl: Uri = data.clipData!!.getItemAt(i).uri
                viewModel.addPhoto(imageUrl)
            }
            viewModel.photoListLiveData.observe(this) {
                adapter = AdPhotosAdapter(viewModel, it, resultLauncher)
                binding.rvPhotos.adapter = adapter
            }
        } else if (data?.data != null) {
            val imageUrl = data.data
            viewModel.addPhoto(imageUrl!!)
            viewModel.photoListLiveData.observe(this) {
                val adapter = AdPhotosAdapter(viewModel, it, resultLauncher)
                binding.rvPhotos.adapter = adapter
            }
        }
    }

    private fun postAdvertisement() {
        binding.llProgressBar.visibility = View.VISIBLE
        val adName = binding.etName.text.toString()
        val adDescription = binding.etDescription.text.toString()
        val adPrice = binding.etPrice.text?.toString()
        var photos: MutableList<Uri>? = null
        val category = binding.spinnerCategory.selectedItem.toString()
        var filters: Map<String, String>? = null
        viewModel.photoListLiveData.observe(this) {
            photos = it
        }
        viewModel.filters.observe(this) {
            filters = it
        }
        if (validateInput(adName, adDescription, adPrice.toString())) {
            val ad = Advertisement(
                photos!!.subList(1, photos!!.size),
                adName, adPrice!!,
                category, filters!!,
                adDescription, System.currentTimeMillis()
            )
            val adId = firebaseDB.collection(DATA_ADS).document()
            adId.set(ad).addOnCompleteListener {
                finish()
            }.addOnFailureListener {
                it.printStackTrace()
                binding.llProgressBar.visibility = View.GONE
                Toast.makeText(this@NewAdActivity, "Failed to post", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.llProgressBar.visibility = View.GONE
        }
    }

    private fun validateInput(name: String?, description: String?, price: String?): Boolean {
        viewModel.isCategorySelected.value = binding.spinnerCategory.selectedItemPosition != 0
        viewModel.isNameValid.value = !name?.isEmpty()!!
        viewModel.isDescriptionValid.value = !description?.isEmpty()!!
        viewModel.isPriceValid.value = (price != null && price != "")

        if (viewModel.canPostAdvertisement()) {
            return true
        } else {
            if (!viewModel.isDescriptionValid.value!!) {
                binding.tilDescription.error = "Empty field"
            }
            if (!viewModel.isNameValid.value!!) {
                binding.tilName.error = "Empty field"
            }
            if (!viewModel.isPriceValid.value!!) {
                binding.tilPrice.error = "Empty field"
            }
            if (!viewModel.isCategorySelected.value!!) {
                binding.spinnerCategory.background =
                    ContextCompat.getDrawable(this, R.drawable.error_shape_red_stroke)
            }
            binding.llProgressBar.visibility = View.GONE
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            addTextChangeListeners()
            return false
        }
    }

    private fun addTextChangeListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilName.error = null
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.etDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilDescription.error = null
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilPrice.error = null
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    companion object {
        fun newIntent(context: Context, userId: String): Intent {
            val intent = Intent(context, NewAdActivity::class.java)
            intent.putExtra(PARAM_USER_ID, userId)
            return intent
        }
    }

}