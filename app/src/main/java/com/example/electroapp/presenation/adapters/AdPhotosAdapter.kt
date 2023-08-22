package com.example.electroapp.presenation.adapters

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.electroapp.R
import com.example.electroapp.data.util.loadFromDrawable
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.presenation.activities.NewAdActivity
import com.example.electroapp.presenation.viewmodels.NewAdViewModel

class AdPhotosAdapter(
    private val viewModel: NewAdViewModel,
    private val photoList: MutableList<String>,
    private val resultLauncher: ActivityResultLauncher<Intent>
) :
    RecyclerView.Adapter<AdPhotosAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
        val deleteImageView: ImageView = itemView.findViewById(R.id.deleteImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horizontal_photo, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if (position == 0) {
            holder.photoImageView.loadUrl(holder.photoImageView.context,"https://static.thenounproject.com/png/187803-200.png")
            holder.deleteImageView.visibility = View.GONE
            holder.photoImageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_OPEN_DOCUMENT
                resultLauncher.launch(Intent.createChooser(intent, "Select images"))
            }
        } else {
            val photoUri = photoList[position]
            holder.photoImageView.loadUrl(holder.photoImageView.context, photoUri.toString())
        }
        holder.deleteImageView.setOnClickListener {
            viewModel.removePhoto(position)
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

}
