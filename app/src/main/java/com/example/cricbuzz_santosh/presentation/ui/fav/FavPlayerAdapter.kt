package com.example.cricbuzz_santosh.presentation.ui.fav

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.databinding.AdapterFavPlayerBinding


class FavPlayerAdapter(val favouritePlayerFragment: FavouritePlayerFragment): RecyclerView.Adapter<FavPlayerAdapter.ViewHolder>() {

    private var list= mutableListOf<Player>()

    class ViewHolder(val binding: AdapterFavPlayerBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return (ViewHolder(
            AdapterFavPlayerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.ivPlayerImageHome.setImageBitmap(BitmapFactory.decodeByteArray(list[position].profilePhoto,0,list[position].profilePhoto!!.size))
        holder.binding.tvPlayerName.text = list[position].name
        holder.binding.tvPlayerCountry.text = list[position].country
        Log.d("In FAvPlayer Adapter","list size ${list.size}")

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updatePlayerList(list1:MutableList<Player>){
        list.clear()
        for (i in list1){
            if(i.fav==1)
                list.add(i)
        }
        notifyDataSetChanged()

    }
}