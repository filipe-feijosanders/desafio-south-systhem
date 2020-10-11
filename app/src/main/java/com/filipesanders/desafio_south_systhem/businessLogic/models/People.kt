package com.filipesanders.desafio_south_systhem.businessLogic.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class People(

    @SerializedName("picture")
    val picture: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("eventId")
    val eventId: String? = null,

    @SerializedName("id")
    val id: String? = null

): Parcelable