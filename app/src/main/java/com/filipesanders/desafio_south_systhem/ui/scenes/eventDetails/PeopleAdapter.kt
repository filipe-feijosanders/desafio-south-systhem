package com.filipesanders.desafio_south_systhem.ui.scenes.eventDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.filipesanders.desafio_south_systhem.R
import com.filipesanders.desafio_south_systhem.businessLogic.models.People
import kotlinx.android.synthetic.main.people_cell.view.*

class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    var listItem: ArrayList<People> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.people_cell, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        val view = holder.itemView

        with(view) {

            Glide.with(poeplePicture)
                .load(item?.picture)
                .error(R.drawable.image_error)
                .transform(
                    MultiTransformation(
                        CenterCrop()
                    )
                )
                .into(poeplePicture)

            peopleName.text = item?.name
        }
    }

    fun submitList(newList: ArrayList<People>) {
        listItem = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = listItem.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}