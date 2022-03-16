package com.wingify.wingifyaudiobook

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout

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
        val filterIcon: ImageView = findViewById(R.id.filter_icon)
        val shimmerFrameLayout: ShimmerFrameLayout = findViewById(R.id.shimmer_layout)
        setSupportActionBar(toolbar);
        artistGenreAdapter = ArtistGenreAdapter(this, dataMap, keyList, this)
        val recyclerView: RecyclerView = findViewById(R.id.artist_genre_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = artistGenreAdapter
        audioBooksViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[AudioBookViewModel:: class.java]
        //progressBar.visibility = View.VISIBLE
        //shuffle.visibility = View.GONE
        shimmerFrameLayout.visibility = View.VISIBLE
        shimmerFrameLayout.startShimmerAnimation()
        recyclerView.visibility = View.GONE
        audioBooksViewModel.fetchData()
        audioBooksViewModel.booksLive.observe(this, object: Observer<List<Result?>> {
            override fun onChanged(t: List<Result?>) {
                //progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                //shuffle.visibility = View.VISIBLE
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmerAnimation()
                if(t.isNotEmpty() && t!=null) {
                    dataMap = audioBooksViewModel.mapData(keyName)
                    keyList = dataMap.keys.toList() as ArrayList<String>
                    artistGenreAdapter.update(dataMap, keyList)

                }

            }
        })
        retryIcon.setOnClickListener {
            //progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            //shuffle.visibility = View.GONE
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmerAnimation()
            audioBooksViewModel.fetchData()

        }
        shuffle.setOnClickListener {
            keyList.shuffle()
            artistGenreAdapter.update(dataMap, keyList)

        }
        filterIcon.setOnClickListener {
            showAlert()

        }
    }

    private fun showAlert(){

        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val alertLayout: View = layoutInflater.inflate(R.layout.alert_dialog, null)
        alertDialogBuilder.setView(alertLayout)
        val filterRadio: RadioGroup = alertLayout.findViewById(R.id.radio_group_filter)
        val filterArtist: RadioButton = alertLayout.findViewById(R.id.radio_button_artist_name)
        val filterGenre: RadioButton = alertLayout.findViewById(R.id.radio_button_genre_name)
        if(keyName == "primaryGenreName"){
            filterRadio.check(R.id.radio_button_genre_name)

        }
        else{
            filterRadio.check(R.id.radio_button_artist_name)

        }
        alertDialogBuilder.setPositiveButton("Apply", object: DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                keyName = if (filterArtist.isChecked && !filterGenre.isChecked) {
                    "artistName"

                } else {
                    "primaryGenreName"

                }
                dataMap = audioBooksViewModel.mapData(keyName)
                keyList = dataMap.keys.toList() as ArrayList<String>
                artistGenreAdapter.update(dataMap, keyList)
            }
        })
        alertDialogBuilder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {

            }

        })
        alertDialogBuilder.setCancelable(true)
        val dialog: AlertDialog = alertDialogBuilder.create()
        dialog.show()

    }

    override fun setOnExpandClick(position: Int) {

    }
}