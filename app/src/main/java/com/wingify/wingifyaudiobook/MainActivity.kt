package com.wingify.wingifyaudiobook

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var audioBooksViewModel: AudioBookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        audioBooksViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[AudioBookViewModel:: class.java]
        audioBooksViewModel.fetchData()
        audioBooksViewModel.booksLive.observe(this, object: Observer<List<Result?>> {
            override fun onChanged(t: List<Result?>) {
                if(t.isEmpty()){
                    Snackbar.make(findViewById(android.R.id.content), "Something went wrong please try again", Snackbar.LENGTH_LONG).show()
                }
                // newsAdapter.update(t as List<Result>)

            }
        })
    }
}