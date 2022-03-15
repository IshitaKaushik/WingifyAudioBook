package com.wingify.wingifyaudiobook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AudioBooksAdapter(private val context: Context) : RecyclerView.Adapter<AudioBooksAdapter.SongViewHolder>() {

    var itemsData = emptyList<Any>()

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var songName: TextView = itemView.findViewById(R.id.songName)
        var songImage: ImageView = itemView.findViewById(R.id.songImage)
        var songDescription: ImageView = itemView.findViewById(R.id.songDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val v1: View = LayoutInflater.from(context).inflate(R.layout.audiobook_recyclerview_item, parent, false)
        return SongViewHolder(v1)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return itemsData.size
    }

    /*fun update(items: List<Article>){
        itemsData.clear()
        itemsData.addAll(items)
        notifyDataSetChanged()
    }*/

}