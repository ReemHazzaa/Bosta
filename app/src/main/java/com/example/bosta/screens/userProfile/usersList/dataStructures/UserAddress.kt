package com.example.bosta.screens.userProfile.usersList.dataStructures

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAddress(
    @SerializedName("street"  ) var street  : String? = null,
    @SerializedName("suite"   ) var suite   : String? = null,
    @SerializedName("city"    ) var city    : String? = null,
    @SerializedName("zipcode" ) var zipcode : String? = null,
    @SerializedName("geo"     ) var geo     : UserAddressGeo?    = UserAddressGeo()
) : Parcelable
