package com.example.bosta.screens.userProfile.usersList.dataStructures

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAddressGeo(
    @SerializedName("lat" ) var lat : String? = null,
    @SerializedName("lng" ) var lng : String? = null
) : Parcelable
