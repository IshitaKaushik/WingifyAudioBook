package com.wingify.wingifyaudiobook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AudioBooksAdapter(private val context: Context, private val itemsData: ArrayList<Result>) : RecyclerView.Adapter<AudioBooksAdapter.SongViewHolder>() {

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var songName: TextView = itemView.findViewById(R.id.songName)
        var songImage: ImageView = itemView.findViewById(R.id.songImage)
        var songDescription: TextView = itemView.findViewById(R.id.songDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val v1: View = LayoutInflater.from(context).inflate(R.layout.audiobook_recyclerview_item, parent, false)
        return SongViewHolder(v1)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.songName.text = itemsData[position].trackName
        holder.songDescription.text = itemsData[position].description
        Glide.with(holder.songImage.context).load(itemsData[position].artworkUrl100).into(holder.songImage)
    }

    override fun getItemCount(): Int {
        return itemsData.size
    }

}