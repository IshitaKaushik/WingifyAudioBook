package com.wingify.wingifyaudiobook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var audioBooksViewModel: AudioBookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar);
        audioBooksViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[AudioBookViewModel:: class.java]
        audioBooksViewModel.fetchData()
        audioBooksViewModel.booksLive.observe(this, object: Observer<List<Result?>> {
            override fun onChanged(t: List<Result?>) {
                // newsAdapter.update(t as List<Result>)

            }
        })
    }
}