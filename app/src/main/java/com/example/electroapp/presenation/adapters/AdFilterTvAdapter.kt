package com.example.electroapp.presenation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electroapp.R
import com.example.electroapp.presenation.viewmodels.NewAdViewModel

class AdFilterTvAdapter(
    private val filterMap: Map<String, String>,
): RecyclerView.Adapter<AdFilterTvAdapter.Holder>() {
    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvFilterName: TextView = view.findViewById(R.id.tv_filter_name)
        val tvFilter: TextView = view.findViewById(R.id.tv_filter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_tv_filters, parent, false
        )
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return filterMap.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val filterEntry = filterMap.entries.elementAt(position)
        val filterName = filterEntry.key
        val filter = filterEntry.value
        holder.tvFilterName.text = filterName
        holder.tvFilter.text = filter
    }
}