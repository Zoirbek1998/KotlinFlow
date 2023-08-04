package com.example.kotlinflow.network


import com.example.kotlinflow.models.GitHubUsers
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiServices {

    @GET("users")
    fun getUsers(): Flow<List<GitHubUsers>>


}