package com.filipesanders.desafio_south_systhem.businessLogic.models

import com.google.gson.annotations.SerializedName

data class People(

    @SerializedName("picture")
    val picture: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("eventId")
    val eventId: String? = null,

    @SerializedName("id")
    val id: String? = null

)