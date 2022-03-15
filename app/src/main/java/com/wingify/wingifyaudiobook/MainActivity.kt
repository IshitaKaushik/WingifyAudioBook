package com.wingify.wingifyaudiobook

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ArtistGenreAdapter.OnExpanded {

    private lateinit var audioBooksViewModel: AudioBookViewModel
    private lateinit var artistGenreAdapter: ArtistGenreAdapter
    private var keyName = "artistName"
    private var keyList: ArrayList<String> = ArrayList()
    private var dataMap: MutableMap<String, ArrayList<Result>> =  mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        val retryIcon: ImageView = findViewById(R.id.retry_icon)
        val progressBar : ProgressBar = findViewById(R.id.progress_bar)
        val shuffle: LinearLayout = findViewById(R.id.shuffle_list)
        setSupportActionBar(toolbar);
        artistGenreAdapter = ArtistGenreAdapter(this, dataMap, keyList, this)
        val recyclerView: RecyclerView = findViewById(R.id.artist_genre_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = artistGenreAdapter
        audioBooksViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[AudioBookViewModel:: class.java]
        progressBar.visibility = View.VISIBLE
        shuffle.visibility = View.GONE
        audioBooksViewModel.fetchData()
        audioBooksViewModel.booksLive.observe(this, object: Observer<List<Result?>> {
            override fun onChanged(t: List<Result?>) {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                shuffle.visibility = View.VISIBLE
                if(t.isNotEmpty() && t!=null) {
                    dataMap = audioBooksViewModel.mapData(keyName)
                    keyList = dataMap.keys.toList() as ArrayList<String>
                    artistGenreAdapter.update(dataMap, keyList)

                }

            }
        })
        retryIcon.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            shuffle.visibility = View.GONE
            audioBooksViewModel.fetchData()

        }
        shuffle.setOnClickListener {
            keyList.shuffle()
            artistGenreAdapter.update(dataMap, keyList)

        }
    }

    override fun setOnExpandClick(position: Int) {

    }
}