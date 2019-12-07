package com.example.booksearchapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.R
import com.example.booksearchapp.adapters.BookListAdapter
import com.example.booksearchapp.constants.AppConstants.BOOK_LABEL
import com.example.booksearchapp.entities.Book
import com.example.booksearchapp.listeners.OnListItemClickListener
import com.example.booksearchapp.services.BookResponse
import com.example.booksearchapp.services.RetrofitManager
import com.mancj.materialsearchbar.MaterialSearchBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookListActivity : AppCompatActivity(), OnListItemClickListener, Callback<BookResponse> {

    private var booksList: ArrayList<Book> = ArrayList()
    private var searchbar: MaterialSearchBar? = null
    private var bookListView: RecyclerView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        searchbar = findViewById(R.id.searchBar)
        bookListView = findViewById(R.id.booksListView)
        progressBar = findViewById(R.id.progressBar)

        initUI()
    }

    private fun initUI() {
        progressBar?.visibility = View.VISIBLE
        RetrofitManager.initialize()
        var bookService = RetrofitManager.getBookService().getBooks()
        bookService.enqueue(this)
    }

    override fun onListItemClick(book: Book) {
        var intent = Intent(this, BookDetailsActivity::class.java)
        intent.putExtra(BOOK_LABEL, book)
        startActivity(intent)
    }

    override fun onFailure(call: Call<BookResponse>, t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
        progressBar?.visibility = View.GONE
        if (response.body()?.numFound!! > 0) {
            booksList = response.body()?.docs!!
            booksList = booksList.filter { item -> item.title != null && item.authorName != null } as ArrayList<Book>
            bookListView?.layoutManager = LinearLayoutManager(this)
            val bookListAdapter = BookListAdapter(booksList, this, this)
            bookListView?.adapter = bookListAdapter

            searchbar?.addTextChangeListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    bookListAdapter.filter.filter(p0)
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
    }
}
