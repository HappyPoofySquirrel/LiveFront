package com.guvyerhopkins.livefront.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

/**
 * I used these resources to architect the networking layer using coroutines and the paging library
 * https://proandroiddev.com/playing-with-4f21bc67a7f9
 * https://github.com/PhilippeBoisney/GithubApp
 */

class PexelsDataSourceFactory(
    var query: String = "pugs", //lets set a fun initial search term
    private val scope: CoroutineScope,
    private val pexelsDataSource: PexelsDataSource = PexelsDataSource(query, scope)
) : DataSource.Factory<Int, Photo>() {

    private val _source = MutableLiveData<PexelsDataSource>()
    val source: LiveData<PexelsDataSource> = _source

    override fun create(): DataSource<Int, Photo> {
        this._source.postValue(pexelsDataSource)
        return pexelsDataSource
    }

    fun updateQuery(query: String) {
        this.query = query
        _source.value?.refresh()
    }
}