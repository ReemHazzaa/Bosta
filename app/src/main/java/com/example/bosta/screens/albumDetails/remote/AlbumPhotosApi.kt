package com.example.bosta.screens.albumDetails.remote

import com.example.bosta.screens.albumDetails.dataStructures.AlbumPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumPhotosApi {
    @GET("photos")
    suspend fun getAlbumPhotos(
        @Query("albumId") albumId: Int
    ): Response<List<AlbumPhoto?>?>
}