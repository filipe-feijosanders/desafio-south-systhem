package com.filipesanders.desafio_south_systhem.businessLogic.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventsResponse(

    @SerializedName("people")
    val people: ArrayList<People> = ArrayList(),

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("longitude")
    val longitude: String? = null,

    @SerializedName("latitude")
    val latitude: String? = null,

    @SerializedName("price")
    val price: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("id")
    val id: String? = null

) : Parcelable {

    class Diffutil : DiffUtil.ItemCallback<EventsResponse>() {
        override fun areItemsTheSame(oldItem: EventsResponse, newItem: EventsResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventsResponse, newItem: EventsResponse): Boolean {
            return oldItem == newItem
        }

    }

}