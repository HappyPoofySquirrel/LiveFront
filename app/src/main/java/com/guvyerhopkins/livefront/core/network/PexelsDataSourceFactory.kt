package com.guvyerhopkins.livefront.core.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

class PexelsDataSourceFactory(
    var query: String = "pugs", //lets set a fun initial search term
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Photo>() {

    val source = MutableLiveData<PexelsDataSource>()

    override fun create(): DataSource<Int, Photo> {
        val source = PexelsDataSource(query, scope)
        this.source.postValue(source)
        return source
    }

    fun updateQuery(query: String) {
        this.query = query
        source.value?.refresh()
    }
}