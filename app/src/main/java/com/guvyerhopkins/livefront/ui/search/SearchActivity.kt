package com.guvyerhopkins.livefront.ui.search

import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.guvyerhopkins.livefront.R
import com.guvyerhopkins.livefront.core.network.NetworkState
import com.guvyerhopkins.livefront.ui.detail.ImageDetailActivity

class SearchActivity : AppCompatActivity() {

    /**
     * Todo Bonus
     * handle no internet
     * Write documentation
     * Add swipe to refresh
     * More Unit tests
     * Cache responses on the search screen
     * Night mode
     * Shared element transition for image press to detail view
     */
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(findViewById(R.id.toolbar))

        progressBar = findViewById(R.id.search_pb)

        searchViewModel =
            ViewModelProvider(this, SearchViewModelFactory()).get(SearchViewModel::class.java)
        val adapter = PhotoGridAdapter { photo, imageView ->
            //keep imageview parameter for use with a shared element transition
            startActivity(ImageDetailActivity.createIntent(this, photo))
        }
        val recyclerView = findViewById<RecyclerView>(R.id.search_rv)
        recyclerView.adapter = adapter

        searchViewModel.photos.observe(this, { photos ->
            adapter.submitList(photos)
        })

        val editText = findViewById<EditText>(R.id.search_et)
        editText.addTextChangedListener {
            searchViewModel.search(it.toString())
        }

        searchViewModel.networkState?.observe(this, {
            progressBar.isVisible = it == NetworkState.LOADING
            if (it == NetworkState.ERROR) {
                Toast.makeText(this, getString(R.string.search_error), Toast.LENGTH_LONG).show()
            } else if (it == NetworkState.ZERORESULTS) {
                Toast.makeText(this, getString(R.string.no_search_results), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}