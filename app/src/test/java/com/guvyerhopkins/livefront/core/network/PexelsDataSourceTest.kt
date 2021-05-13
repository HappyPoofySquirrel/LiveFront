package com.guvyerhopkins.livefront.core.network

import androidx.paging.PageKeyedDataSource
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class PexelsDataSourceTest {

    private lateinit var dataSource: PexelsDataSource

    private val query: String = "query"

    private val pexelsApiService: PexelsApiService = mockk()

    private val repo: PexelsRepository = mockk()

    private var job: Job = mockk()

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    @Before
    fun setUp() {
        dataSource = PexelsDataSource(query, scope, pexelsApiService, repo, job)
    }

    @Test
    fun loadInitial() = runBlocking {
        val initialCallback = mockk<PageKeyedDataSource.LoadInitialCallback<Int, Photo>>()

        dataSource.loadInitial(mockk(), initialCallback)

        verify(repo).getPhotos(anyInt(), anyInt(), anyString())
        verify(initialCallback).onResult(any(), null, anyInt())
    }

    @Test
    fun nullQuery_doesNotCallRepo() = runBlocking {
        dataSource = PexelsDataSource("", scope, pexelsApiService, repo, job)

        dataSource.loadInitial(mockk(), mockk())

        verifyNoInteractions(repo) //cant use a mockito verify with a mockk ovariable
    }
}