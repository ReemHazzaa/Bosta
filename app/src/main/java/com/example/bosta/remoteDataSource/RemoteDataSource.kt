package com.example.bosta.remoteDataSource

import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum
import com.example.bosta.screens.userProfile.userAlbums.remote.UserAlbumsListApi
import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import com.example.bosta.screens.userProfile.usersList.remote.UsersListApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val usersListApi: UsersListApi,
    private val userAlbumsListApi: UserAlbumsListApi
) {
    suspend fun getUsersList(): Response<List<User?>?> {
        return usersListApi.getUsersList()
    }

    suspend fun getUserAlbumsList(userId: Int): Response<List<UserAlbum?>?> {
        return userAlbumsListApi.getUserAlbumsList(userId)
    }
}