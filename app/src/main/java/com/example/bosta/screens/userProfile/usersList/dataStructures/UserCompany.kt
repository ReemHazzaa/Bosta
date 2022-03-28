package com.example.bosta.screens.userProfile.usersList.dataStructures

import com.google.gson.annotations.SerializedName

data class UserCompany(
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("catchPhrase" ) var catchPhrase : String? = null,
    @SerializedName("bs"          ) var bs          : String? = null
)
