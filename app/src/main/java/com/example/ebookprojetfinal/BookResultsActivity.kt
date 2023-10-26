package com.example.ebookprojetfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebookprojetfinal.databinding.ActivityBookResultsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BookResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookResultsBinding.inflate(layoutInflater)
        var view: View = binding.root
        setContentView(view)
        setViewItems()
    }

    private fun setViewItems() {
        val searchInput = intent.getStringExtra("search")
        callService(searchInput.toString())
    }

    private fun callService(search: String) {
        val service: BookApi.BookService = BookApi().getClient().create(BookApi.BookService::class.java)
        val call: Call<BookApiResponce> = service.getBooks(search)
        call.enqueue(object : Callback<BookApiResponce> {
            override fun onResponse(call: Call<BookApiResponce>, response: Response<BookApiResponce>) {
                processResponse(response)
            }

            override fun onFailure(call: Call<BookApiResponce>, t: Throwable) {
                processFailure(t)
            }
        })
    }

    private fun processFailure(t: Throwable) {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show()
        t.message?.let { Log.d("Erreur", it) }
    }

    private fun processResponse(response: Response<BookApiResponce>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.items!!.isNotEmpty()) {
                val adapter = BookListViewAdapter(body.items, object : BookItemCallback {
                    override fun onCellClick(data: Items) {
                        gotoNextActivity(data)
                    }

                    override fun onSaveBook(book: Items) {
                        saveBook(book)
                    }
                })
                val recyclerView = findViewById<RecyclerView>(R.id.book_rv)
                binding.bookRv.adapter = adapter
                binding.bookRv.layoutManager = LinearLayoutManager(applicationContext)
            }
        } else {
            Toast.makeText(this, "Erreur lors de la récupération des données", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveBook(book: Items) {
        if (SharedPreferencesManager().saveBook(book, this)){
            Toast.makeText(this,"Enregistrement bien effectué", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,"Ce livre est déjà dans vos favoris", Toast.LENGTH_LONG).show()
        }
    }


    private fun gotoNextActivity(data: Items) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra(MainActivity.bookKey, data.volumeInfo?.title)
        intent.putExtra(MainActivity.imageKey, data.volumeInfo?.imageLinks?.thumbnail)
        intent.putExtra(MainActivity.authorKey, data.volumeInfo?.authors?.get(0))
        intent.putExtra(MainActivity.descriptionKey, data.volumeInfo?.description)
        startActivity(intent)
    }
}