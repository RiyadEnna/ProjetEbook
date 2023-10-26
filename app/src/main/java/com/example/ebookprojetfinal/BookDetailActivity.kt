package com.example.ebookprojetfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.ebookprojetfinal.databinding.ActivityBookDetailBinding


class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        val bookTitle = intent.getStringExtra(MainActivity.bookKey)
        val bookImage = intent.getStringExtra(MainActivity.imageKey)
        val bookAuthor = intent.getStringExtra(MainActivity.authorKey)
        val bookDescription = intent.getStringExtra(MainActivity.descriptionKey)

        binding.bookTitleTv.text = bookTitle
        binding.bookAuthorDetailTv.text = bookAuthor
        binding.bookDescriptionTv.text = bookDescription
        Glide.with(binding.bookIv.context).load(bookImage)
            .into(binding.bookIv)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
