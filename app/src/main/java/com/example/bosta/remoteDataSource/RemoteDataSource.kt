package com.example.bosta.remoteDataSource

import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import com.example.bosta.screens.userProfile.usersList.remote.UsersListApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val usersListApi: UsersListApi
) {
    suspend fun getJobs(): Response<List<User?>?> {
        return usersListApi.getUsersList()
    }
}