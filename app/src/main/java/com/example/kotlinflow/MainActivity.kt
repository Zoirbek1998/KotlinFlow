package com.example.kotlinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinflow.vm.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var userViewModel: UserViewModel
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]


//        liveDara orqli ishlatish
//        userViewModel.getUserLiveData().observe(this, Observer {
//             when{
//                 it.isFailure ->{
//                     Log.d(TAG, "onCreate: ${it.getOrNull()}")
//                 }
//                 it.isSuccess ->{
//                     Log.d(TAG, "onCreate: ${it.exceptionOrNull()}")
//                 }
//             }
//        })


        //       stateFlow  orqli ishlatish
        launch {
            userViewModel.getUserLiveData().collect {
                when {
                    it.isFailure -> {
                        Log.d(TAG, "onCreate: ${it.getOrNull()}")
                    }

                    it.isSuccess -> {
                        Log.d(TAG, "onCreate: ${it.exceptionOrNull()}")
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}