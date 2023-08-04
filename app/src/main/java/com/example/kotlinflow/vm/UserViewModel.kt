package com.example.kotlinflow.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflow.models.GitHubUsers
import com.example.kotlinflow.network.ApiClient
import com.example.kotlinflow.network.ApiServices
import com.example.kotlinflow.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val apiServices = ApiClient.getRetrofit().create(ApiServices::class.java)
    private val userRepository = UserRepository(apiServices)

    private val liveData = MutableLiveData<Result<List<GitHubUsers>?>>()

    //      liveData ni orninni bosadigon
    private val stateFlow =
        MutableStateFlow<Result<List<GitHubUsers>?>>(Result.success(emptyList()))

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            userRepository.getGithubUsers()
                .catch {
                    liveData.postValue(Result.failure(it))
                }
                .collect {
                    if ((it.isNotEmpty())) {
//                        liveData.postValue(Result.success(it.body()))
                        stateFlow.emit(Result.success(it))
                    } else {
//                        liveData.postValue(Result.failure(Throwable("Error")))
                        stateFlow.emit(Result.failure(Throwable("Error")))

                    }
                }
        }
    }

    fun getUserLiveData(): StateFlow<Result<List<GitHubUsers>?>> {
        return stateFlow
    }
}