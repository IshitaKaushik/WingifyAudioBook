package com.wingify.wingifyaudiobook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool

class ArtistGenreAdapter(private val context: Context) : RecyclerView.Adapter<ArtistGenreAdapter.ArtistViewHolder>() {

    private val viewPool = RecycledViewPool()
    var itemsData = emptyList<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val v1: View = LayoutInflater.from(context).inflate(R.layout.artist_genre_recyclerview_item, parent, false)
        return ArtistViewHolder(v1)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val mLayoutManager= LinearLayoutManager(holder.songsRecyclerView.context)
        val songsAdapter = AudioBooksAdapter(context)
        holder.songsRecyclerView.layoutManager = mLayoutManager
        holder.songsRecyclerView.adapter = songsAdapter
        holder.songsRecyclerView.setRecycledViewPool(viewPool)
    }

    override fun getItemCount(): Int {
        return itemsData.size
    }
    class ArtistViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var artistName: TextView = itemView.findViewById(R.id.artist_genre_name)
        var songsRecyclerView: RecyclerView = itemView.findViewById(R.id.artist_recycler_view)

    }

    interface onExpanded {
        public fun setOnExpandClick(position: Int)
    }
}