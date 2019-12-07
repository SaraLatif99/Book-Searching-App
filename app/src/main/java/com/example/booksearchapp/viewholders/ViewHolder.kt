package com.example.booksearchapp.viewholders

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.booksearchapp.R;

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bookName = view.findViewById<TextView>(R.id.bookName)
    val authorName = view.findViewById<TextView>(R.id.authorName)
    val publishingYear = view.findViewById<TextView>(R.id.publishingYear)
    val bookCover = view.findViewById<ImageView>(R.id.bookCover)
}