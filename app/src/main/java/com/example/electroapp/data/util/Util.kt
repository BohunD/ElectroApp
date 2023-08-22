package com.example.electroapp.data.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.electroapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ImageView.loadUrl(context: Context,url: String?, errorDrawable: Int = R.drawable.error_image){
    val options = RequestOptions()
        .placeholder(progressDrawable(context))
        .error(errorDrawable)
    Glide.with(context).load(url).apply(options).into(this)
}

fun ImageView.loadFromDrawable(path: Int){
    Glide.with(context.applicationContext).load(path).into(this)
}

fun progressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}

fun getDateTimeFromLong(dateTime: Long?): String {
    dateTime?.let {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(it))
    }
    return "Unknown"
}