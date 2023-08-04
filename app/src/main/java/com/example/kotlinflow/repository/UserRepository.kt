package com.example.kotlinflow.repository

import com.example.kotlinflow.network.ApiServices
import kotlinx.coroutines.flow.flow

class UserRepository(private val apiServices: ApiServices) {

    fun getGithubUsers() = apiServices.getUsers()
}