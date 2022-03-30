package com.example.bosta.screens.userProfile.usersList.dataStructures

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCompany(
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("catchPhrase" ) var catchPhrase : String? = null,
    @SerializedName("bs"          ) var bs          : String? = null
) : Parcelable
