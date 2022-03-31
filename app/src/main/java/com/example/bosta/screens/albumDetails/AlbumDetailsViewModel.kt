package com.example.bosta.screens.albumDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bosta.R
import com.example.bosta.remoteDataSource.NetworkResult
import com.example.bosta.remoteDataSource.RemoteDataSource
import com.example.bosta.screens.albumDetails.dataStructures.AlbumPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    application: Application
): AndroidViewModel(application){

    var photosResponse: MutableLiveData<NetworkResult<List<AlbumPhoto?>?>> = MutableLiveData()

    fun getAlbumPhotos(albumID: Int) = viewModelScope.launch {
        getPhotosSafeCall(albumID)
    }

    private suspend fun getPhotosSafeCall(albumID: Int) {
        photosResponse.value = NetworkResult.Loading()
            try {
                val response = remoteDataSource.getAlbumPhotos(albumID)
                photosResponse.value = handleUsersResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
                photosResponse.value = NetworkResult.Error(
                    getApplication<Application>().resources.getString(R.string.no_users),
                    null
                )
            }
    }

    private fun handleUsersResponse(response: Response<List<AlbumPhoto?>?>): NetworkResult<List<AlbumPhoto?>?>? {
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