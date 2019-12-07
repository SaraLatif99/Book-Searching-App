package com.example.booksearchapp.services

import retrofit2.Call
import retrofit2.http.GET

interface BookService {
    @GET("search.json?q=fiction")
    fun getBooks(): Call<BookResponse>
}