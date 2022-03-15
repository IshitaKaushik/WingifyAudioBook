package com.wingify.wingifyaudiobook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool

class ArtistGenreAdapter(private val context: Context, private val dataMap: MutableMap<String, ArrayList<Result>>, private val itemsData: ArrayList<String>, val onExpanded: OnExpanded) : RecyclerView.Adapter<ArtistGenreAdapter.ArtistViewHolder>() {

    private val viewPool = RecycledViewPool()
    private var currentItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val v1: View = LayoutInflater.from(context).inflate(R.layout.artist_genre_recyclerview_item, parent, false)
        return ArtistViewHolder(v1)

    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.artistName.text = itemsData[position]
        val mLayoutManager= LinearLayoutManager(holder.songsRecyclerView.context)
        val songsAdapter = dataMap[itemsData[position]]?.let { AudioBooksAdapter(context, it) }
        holder.songsRecyclerView.layoutManager = mLayoutManager
        holder.songsRecyclerView.adapter = songsAdapter
        holder.songsRecyclerView.setRecycledViewPool(viewPool)
        holder.artistName.setOnClickListener{
            onExpanded.setOnExpandClick(holder.adapterPosition)
            if(holder.adapterPosition != currentItem){
                currentItem = holder.adapterPosition
                notifyDataSetChanged()
            }
            if(holder.songsRecyclerView.isVisible){
                holder.songsRecyclerView.visibility = View.GONE
            }
            else{
                holder.songsRecyclerView.visibility = View.VISIBLE
            }
        }
        if(currentItem != position && holder.songsRecyclerView.isVisible){
            holder.songsRecyclerView.visibility = View.GONE

        }
        else if (currentItem == position){
            holder.songsRecyclerView.visibility = View.VISIBLE

        }

    }

    override fun getItemCount(): Int {
        return itemsData.size

    }
    class ArtistViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var artistName: TextView = itemView.findViewById(R.id.artist_genre_name)
        var songsRecyclerView: RecyclerView = itemView.findViewById(R.id.artist_recycler_view)

    }

    fun update(dataMaps: MutableMap<String, ArrayList<Result>>,items: List<String>){
        itemsData.clear()
        itemsData.addAll(items)
        dataMap.clear()
        dataMap.putAll(dataMaps)
        notifyDataSetChanged()
    }

    interface OnExpanded {
        fun setOnExpandClick(position: Int)

    }
}