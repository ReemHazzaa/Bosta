package com.example.bosta.screens.userProfile.usersList.remote

import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import retrofit2.Response
import retrofit2.http.GET

interface UsersListApi {
    @GET("users")
    suspend fun getUsersList(): Response<List<User?>?>
}