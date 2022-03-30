package com.example.bosta.di

import com.example.bosta.common.Constants.Companion.BASE_URL
import com.example.bosta.screens.albumDetails.remote.AlbumPhotosApi
import com.example.bosta.screens.userProfile.userAlbums.remote.UserAlbumsListApi
import com.example.bosta.screens.userProfile.usersList.remote.UsersListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideUsersLIstApiService(retrofit: Retrofit): UsersListApi {
        return retrofit.create(UsersListApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserAlbumsListApiApiService(retrofit: Retrofit): UserAlbumsListApi {
        return retrofit.create(UserAlbumsListApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAlbumPhotosApiService(retrofit: Retrofit): AlbumPhotosApi {
        return retrofit.create(AlbumPhotosApi::class.java)
    }

}