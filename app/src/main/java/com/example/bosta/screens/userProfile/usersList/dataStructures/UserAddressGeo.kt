package com.example.bosta.screens.userProfile.usersList.dataStructures

import com.google.gson.annotations.SerializedName

data class UserAddressGeo(
    @SerializedName("lat" ) var lat : String? = null,
    @SerializedName("lng" ) var lng : String? = null
)
