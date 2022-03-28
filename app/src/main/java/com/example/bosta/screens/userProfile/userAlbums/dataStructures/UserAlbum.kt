package com.example.bosta.screens.userProfile.userAlbums.dataStructures

import com.google.gson.annotations.SerializedName

data class UserAlbum(
    @SerializedName("userId" ) var userId : Int?    = null,
    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("title"  ) var title  : String? = null
)
