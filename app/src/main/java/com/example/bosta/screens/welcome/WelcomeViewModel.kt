package com.example.bosta.screens.welcome

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bosta.R
import com.example.bosta.common.NetworkMonitor
import com.example.bosta.remoteDataSource.NetworkResult
import com.example.bosta.remoteDataSource.RemoteDataSource
import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    application: Application
): AndroidViewModel(application){
    private val isConnected = NetworkMonitor.isNetworkConnected

    var usersResponse: MutableLiveData<NetworkResult<List<User?>?>> = MutableLiveData()

    fun getUsers() = viewModelScope.launch {
        getUsersSafeCall()
    }

    private suspend fun getUsersSafeCall() {
        usersResponse.value = NetworkResult.Loading()
       // if (isConnected) {
            try {
                val response = remoteDataSource.getUsersList()
                usersResponse.value = handleUsersResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
                usersResponse.value = NetworkResult.Error(
                    getApplication<Application>().resources.getString(R.string.no_users),
                    null
                )
            }
        //} else {
          //  usersResponse.value = NetworkResult.Error(
         //       getApplication<Application>().resources.getString(R.string.no_internet_connection),
          //      null
         //   )
      //  }
    }

    private fun handleUsersResponse(response: Response<List<User?>?>?): NetworkResult<List<User?>?> {
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