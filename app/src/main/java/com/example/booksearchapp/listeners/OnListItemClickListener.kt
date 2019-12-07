package com.example.booksearchapp.listeners

import com.example.booksearchapp.entities.Book

interface OnListItemClickListener {
    fun onListItemClick(book: Book)
}