package com.example.booksearchapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksearchapp.R
import com.example.booksearchapp.constants.AppConstants.IMAGE_URL_BASE
import com.example.booksearchapp.entities.Book
import com.example.booksearchapp.listeners.OnListItemClickListener
import com.example.booksearchapp.viewholders.ViewHolder



class BookListAdapter(
    private val books: ArrayList<Book>,
    private val context: Context,
    private val onListItemClickListener: OnListItemClickListener?
) : RecyclerView.Adapter<ViewHolder>(), Filterable {

    private var booksSearchList: List<Book>? = null

    init {
        booksSearchList = books
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.book_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var book: Book = booksSearchList!![position]

        holder.bookName?.text = book.title
        holder.authorName?.text = book.authorName!![0]
        if (book.publishingYear != null) {
            holder.publishingYear?.text = book.publishingYear!![0].toString()
        }
        if (book.coverId != null) {
            var coverUrl: String = IMAGE_URL_BASE + book.coverId + "-S.jpg"
            Glide.with(context).load(coverUrl).into(holder.bookCover)
        }

        holder.itemView.setOnClickListener {
            onListItemClickListener?.onListItemClick(book)
        }
    }

    override fun getItemCount(): Int {
        return booksSearchList!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    booksSearchList = books
                } else {
                    val filteredList = ArrayList<Book>()
                    for (book in books) {
                        if (book.title!!.toLowerCase().contains(charString.toLowerCase()) ||
                                book.authorName!![0].toLowerCase().contains(charString.toLowerCase()) ||
                                (book.publishingYear != null &&
                                        book.publishingYear!![0].toString().contains(charString))) {
                            filteredList.add(book)
                        }
                    }
                    booksSearchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = booksSearchList
                return filterResults
            }
            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                booksSearchList = filterResults.values as ArrayList<Book>
                notifyDataSetChanged()
            }
        }
    }
}