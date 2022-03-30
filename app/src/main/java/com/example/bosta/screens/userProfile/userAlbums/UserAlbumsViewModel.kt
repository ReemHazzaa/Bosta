package com.example.bosta.screens.userProfile.userAlbums

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bosta.R
import com.example.bosta.remoteDataSource.NetworkResult
import com.example.bosta.remoteDataSource.RemoteDataSource
import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserAlbumsViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    application: Application
): AndroidViewModel(application){

    var usersResponse: MutableLiveData<NetworkResult<List<UserAlbum?>?>> = MutableLiveData()

    fun getAlbums(userID: Int) = viewModelScope.launch {
        getAlbumsSafeCall(userID)
    }

    private suspend fun getAlbumsSafeCall(userID: Int) {
        usersResponse.value = NetworkResult.Loading()
            try {
                val response = remoteDataSource.getUserAlbumsList(userID)
                usersResponse.value = handleUsersResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
                usersResponse.value = NetworkResult.Error(
                    getApplication<Application>().resources.getString(R.string.no_users),
                    null
                )
            }
    }

    private fun handleUsersResponse(response: Response<List<UserAlbum?>?>): NetworkResult<List<UserAlbum?>?>? {
        when {
            response == null -> {
                return NetworkResult.Error(
                    getApplication<Application>().resources.getString(R.string.no_api_response),
                    null
                )
            }
            response.body() == null -> {
                return NetworkResult.Error(
                    getApplication<Application>().resources.getString(R.string.no_users),
                    null
                )
            }
            response.body()!!.isNullOrEmpty() -> {
                return NetworkResult.Error(
                    getApplication<Application>().resources.getString(R.string.no_users),
                    null
                )
            }
            response.isSuccessful -> {
                val users = response.body()
                return NetworkResult.Success(users!!)
            }
            else -> {
                return NetworkResult.Error(response.message(), null)
            }
        }
    }
}