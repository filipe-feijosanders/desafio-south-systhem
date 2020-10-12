package com.filipesanders.desafio_south_systhem.ui.scenes.events

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.filipesanders.desafio_south_systhem.R
import com.filipesanders.desafio_south_systhem.businessLogic.models.EventsResponse
import kotlinx.android.synthetic.main.events_list_cell.view.*

/*
Em um dos commits, adicionei classes para realizar paginação através do adapter (por paginação ser algo comum).
Porém, lembrando que não sei se o servidor está dando suporte para paginação (e suspeito que não),
optei por fazer um adapter comum.
*/

class EventsAdapter() : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    var listItem: ArrayList<EventsResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.events_list_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        val view = holder.itemView

        with(view) {

            eventsTitle.text = item?.title

            Glide.with(eventsImage)
                .load(item?.image)
                .error(R.drawable.image_error)
                .transform(
                    MultiTransformation(
                        CenterCrop()
                    )
                )
                .into(eventsImage)

            this.setOnClickListener {

                findNavController().navigate(
                    EventsFragmentDirections.actionHomeFragmentToEventDetailsFragment(
                        eventId = item?.id,
                        latitude = item?.latitude,
                        longitude = item?.longitude
                    )
                )

            }
        }
    }

    override fun getItemCount() = listItem.size

    fun submitList(newList: ArrayList<EventsResponse>) {
        listItem = newList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
