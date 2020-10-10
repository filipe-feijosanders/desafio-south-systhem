package com.filipesanders.desafio_south_systhem.businessLogic.models

import com.google.gson.annotations.SerializedName

data class CheckinRequest(

    @SerializedName("eventId")
    val eventId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null

)