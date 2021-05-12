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
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Photo>() {

    private val _source = MutableLiveData<PexelsDataSource>()
    val source: LiveData<PexelsDataSource> = _source

    override fun create(): DataSource<Int, Photo> {
        val source = PexelsDataSource(query, scope)
        this._source.postValue(source)
        return source
    }

    fun updateQuery(query: String) {
        this.query = query
        _source.value?.refresh()
    }
}