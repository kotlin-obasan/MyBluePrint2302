package com.example.myblueprint2302.ui.main.data.repository

import com.example.myblueprint2302.ui.main.data.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import retrofit2.Response

inline fun <reified T : Any> apiFlow(crossinline call: suspend () -> Response<T>): Flow<ApiStatus<T>> =
    flow<ApiStatus<T>> {
        val response = call()
        if (response.isSuccessful) emit(ApiStatus.Success(value = response.body()!!))
        else throw HttpException(response)
    }.onStart {
        emit(ApiStatus.Proceeding)
    }.flowOn(Dispatchers.IO)
