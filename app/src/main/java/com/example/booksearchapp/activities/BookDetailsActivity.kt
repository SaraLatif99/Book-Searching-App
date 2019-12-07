package com.example.booksearchapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.booksearchapp.R
import com.example.booksearchapp.constants.AppConstants
import com.example.booksearchapp.constants.AppConstants.IMAGE_URL_BASE
import com.example.booksearchapp.entities.Book
import android.content.Intent
import android.view.Menu
import androidx.core.view.MenuItemCompat
import androidx.appcompat.widget.ShareActionProvider

class BookDetailsActivity : AppCompatActivity() {
    private var bookName: TextView? = null
    private var bookAuthor: TextView? = null
    private var publishingYear: TextView? = null
    private var bookCover: ImageView? = null
    private var shareActionProvider: ShareActionProvider? = null
    private var coverUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        bookName = findViewById(R.id.bookDetailsName)
        bookAuthor = findViewById(R.id.bookDetailsAuthor)
        publishingYear = findViewById(R.id.bookDetailsYear)
        bookCover = findViewById(R.id.bookCoverImage)

        val mActionBar: ActionBar? = supportActionBar
        mActionBar?.setDisplayHomeAsUpEnabled(true)
        mActionBar?.setDisplayHomeAsUpEnabled(true)

        setUI()
    }

    private fun setUI() {
        var book = intent.getParcelableExtra<Book>(AppConstants.BOOK_LABEL)
        bookName?.text = book.title
        bookAuthor?.text = book.authorName!![0]
        if (book.publishingYear != null) {
            publishingYear?.text = book.publishingYear!![0].toString()
        }
        if (book.coverId != null) {
            coverUrl = IMAGE_URL_BASE + book.coverId + "-L.jpg"
            Glide.with(this).load(coverUrl).placeholder(R.drawable.img_books_large).into(bookCover)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (coverUrl == null) {
            return false
        }
        menuInflater.inflate(R.menu.share_menu, menu)
        val shareItem = menu.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider?
        setShareIntent()
        return true
    }

    private fun setShareIntent() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            "Book Recommendation!"
        )
        shareIntent.putExtra(Intent.EXTRA_TEXT, coverUrl)
        shareActionProvider?.setShareIntent(shareIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

