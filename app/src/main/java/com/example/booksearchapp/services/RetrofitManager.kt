package com.example.booksearchapp.services

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitManager {

    val base_url = "http://openlibrary.org/"
    lateinit var retrofit: Retrofit


    fun initialize() {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getBookService(): BookService {

        return retrofit.create(BookService::class.java)
    }
}