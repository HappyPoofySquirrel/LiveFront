package com.guvyerhopkins.livefront.core.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*

/**
 * I used these resources to architect the networking layer using coroutines and the paging library
 * https://proandroiddev.com/playing-with-4f21bc67a7f9
 * https://github.com/PhilippeBoisney/GithubApp
 */

class PexelsDataSource(
    private val query: String,
    private val scope: CoroutineScope,
    private val pexelsApiService: PexelsApiService = PexelsApi.retrofitService,
    private val repo: PexelsRepository = PexelsRepository(pexelsApiService),
    private var supervisorJob: Job = SupervisorJob()
) : PageKeyedDataSource<Int, Photo>() {

    private val _networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        if (query.isNotEmpty()) {//this prevents searching on initialization
            executeQuery(1, params.requestedLoadSize) {
                callback.onResult(it, null, 2)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        val page = params.key
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        val page = params.key
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    private fun executeQuery(page: Int, perPage: Int, callback: (List<Photo>) -> Unit) {
        _networkState.postValue(NetworkState.LOADING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200) // To handle user is still typing
            val photos = repo.getPhotos(page, perPage, query)
            _networkState.postValue(NetworkState.SUCCESS)
            callback(photos)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(PexelsResponse::class.java.simpleName, "An error happened: $e")
        _networkState.postValue(NetworkState.ERROR)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()
    }

    fun refresh() = this.invalidate()

    fun getNetworkState(): LiveData<NetworkState> =
        _networkState
}