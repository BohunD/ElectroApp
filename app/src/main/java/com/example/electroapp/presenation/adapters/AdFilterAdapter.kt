package com.example.electroapp.presenation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electroapp.R
import com.example.electroapp.data.util.CATEGORIES
import com.example.electroapp.presenation.viewmodels.NewAdViewModel

class AdFilterAdapter(
    private val filterMap: Map<String, ArrayList<String>>,
    private val viewModel: NewAdViewModel
) :
    RecyclerView.Adapter<AdFilterAdapter.FilterViewHolder>() {

    class FilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFilterName: TextView = view.findViewById(R.id.tv_filter_name)
        val spinner: Spinner = view.findViewById(R.id.spinner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_filter_new_ad_rv, parent, false
        )
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterMap.size
    }

    override fun onBindViewHolder(
        holder: FilterViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.tvFilterName.text = filterMap.keys.elementAt(position)
        val selectedFilters = viewModel.selectedFilters.value
        val filters = selectedFilters?.get(filterMap.keys.elementAt(position))
        val adapter = ArrayAdapter(
            holder.itemView.context,
            android.R.layout.simple_spinner_item,
            filters ?: emptyList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinner.adapter = adapter
        holder.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.addFilters(filterMap.keys.elementAt(position), filters?.get(p2) ?: "")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}