package com.example.bosta.screens.userProfile.userAlbums.remote

import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum
import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAlbumsListApi {
    @GET("albums")
    suspend fun getUserAlbumsList(
        @Query("userId") userId: Int
    ): Response<List<UserAlbum?>?>
}