package com.guvyerhopkins.livefront.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.guvyerhopkins.livefront.core.network.NetworkState
import com.guvyerhopkins.livefront.core.network.PexelsDataSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * I used these resources to architect the networking layer using coroutines and the paging library
 * https://proandroiddev.com/playing-with-4f21bc67a7f9
 * https://github.com/PhilippeBoisney/GithubApp
 */

class SearchViewModel(
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val dataSourceFactory: PexelsDataSourceFactory = PexelsDataSourceFactory(scope = ioScope)
) : ViewModel() {

    val networkState: LiveData<NetworkState> =
        switchMap(dataSourceFactory.source) { it.getNetworkState() }

    val photos = LivePagedListBuilder(
        dataSourceFactory, PagedList.Config.Builder()
            .setInitialLoadSizeHint(30)
            .setEnablePlaceholders(false)
            .setPageSize(30 * 2)
            .build()
    ).build()

    fun search(query: String) {
        if (query == dataSourceFactory.query) {
            return//return if query did not change
        }

        if (query.length > 2) {
            dataSourceFactory.updateQuery(query.trim())
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.coroutineContext.cancel()
    }
}