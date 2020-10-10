package com.filipesanders.desafio_south_systhem.businessLogic.models

import com.google.gson.annotations.SerializedName

data class EventDetailsResponse(

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

)