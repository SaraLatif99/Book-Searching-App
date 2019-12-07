package com.example.booksearchapp.services

import com.example.booksearchapp.entities.Book

class BookResponse {
    var numFound: Int? = 0
    var docs: ArrayList<Book>? = null
}