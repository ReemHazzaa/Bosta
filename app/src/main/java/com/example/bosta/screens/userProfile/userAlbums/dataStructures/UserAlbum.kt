package com.example.bosta.screens.userProfile.userAlbums.dataStructures

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAlbum(
    @SerializedName("userId" ) var userId : Int?    = null,
    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("title"  ) var title  : String? = null
) : Parcelable
