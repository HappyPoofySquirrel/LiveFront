package com.guvyerhopkins.livefront.core.network

/**
 * I used these resources to architect the networking layer using coroutines and the paging library
 * https://proandroiddev.com/playing-with-4f21bc67a7f9
 * https://github.com/PhilippeBoisney/GithubApp
 */

enum class NetworkState {
    SUCCESS, LOADING, ERROR, NOINTERNET
}