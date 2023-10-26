package com.example.ebookprojetfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ebookprojetfinal.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        val bookKey = "bookKey"
        val imageKey = "imageKey"
        val authorKey = "authorKey"
        val descriptionKey = "descriptionKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewItems()
    }

    private fun setViewItems() {
        val storedSearch = SharedPreferencesManager().getSearchCriteria(this)
        if (storedSearch != null) {
            binding.searchEt.setText(storedSearch)
        }

        binding.searchBt.setOnClickListener {
            checkUserInput()
            binding.searchBt.visibility = View.INVISIBLE
            binding.progress.visibility = View.VISIBLE
        }

        binding.favoritesBt.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }

    private fun checkUserInput() {
        if (binding.searchEt.text.toString().isEmpty()) {
            Toast.makeText(this, "Veuillez effectuer une saisie", Toast.LENGTH_LONG).show()
        } else {
            binding.searchBt.visibility = View.VISIBLE
            binding.progress.visibility = View.INVISIBLE
            SharedPreferencesManager().saveSearchCriteria(binding.searchEt.text.toString(), this)
            goToResultActivity(binding.searchEt.text.toString())
        }
    }

    private fun goToResultActivity(search: String) {
        val intent = Intent(this, BookResultsActivity::class.java)
        intent.putExtra("search", search)
        binding.searchBt.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        binding.searchBt.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
    }


}