package com.guvyerhopkins.livefront.core.network

/**
 * I used these resources to architect the networking layer using coroutines and the paging library
 * https://proandroiddev.com/playing-with-4f21bc67a7f9
 * https://github.com/PhilippeBoisney/GithubApp
 */

class PexelsRepository(private val service: PexelsApiService) {

    private suspend fun search(page: Int, perPage: Int, query: String) =
        service.getPhotos(query, page, perPage)

    suspend fun getPhotos(
        page: Int,
        perPage: Int,
        query: String
    ): List<Photo> {
        if (query.isEmpty()) return listOf()
        val request = search(page, perPage, query)
        return request.photos
    }
}