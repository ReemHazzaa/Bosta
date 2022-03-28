package com.example.bosta.remoteDataSource

import com.example.bosta.screens.albumDetails.dataStructures.AlbumPhoto
import com.example.bosta.screens.albumDetails.remote.AlbumPhotosApi
import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum
import com.example.bosta.screens.userProfile.userAlbums.remote.UserAlbumsListApi
import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import com.example.bosta.screens.userProfile.usersList.remote.UsersListApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val usersListApi: UsersListApi,
    private val userAlbumsListApi: UserAlbumsListApi,
    private val albumPhotosApi: AlbumPhotosApi
) {
    suspend fun getUsersList(): Response<List<User?>?> {
        return usersListApi.getUsersList()
    }

    suspend fun getUserAlbumsList(userId: Int): Response<List<UserAlbum?>?> {
        return userAlbumsListApi.getUserAlbumsList(userId)
    }

    suspend fun getAlbumPhotos(albumId: Int): Response<List<AlbumPhoto?>?> {
        return albumPhotosApi.getAlbumPhotos(albumId)
    }
}