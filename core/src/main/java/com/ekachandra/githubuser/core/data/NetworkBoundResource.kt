package com.ekachandra.githubuser.core.data

import com.ekachandra.githubuser.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<com.ekachandra.githubuser.core.data.Resource<ResultType>> = flow {
        emit(com.ekachandra.githubuser.core.data.Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map {
                    com.ekachandra.githubuser.core.data.Resource.Success(it)
                })
            }

            is ApiResponse.Empty -> {
                com.ekachandra.githubuser.core.data.Resource.Success(null)
            }

            is ApiResponse.Error -> {
                onFetchFailed()
                emit(com.ekachandra.githubuser.core.data.Resource.Error(apiResponse.errorMessage))
            }

        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<com.ekachandra.githubuser.core.data.Resource<ResultType>> = result
}