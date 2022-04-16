package com.example.cricbuzz_santosh.presentation.ui.bowler

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.databinding.FragmentBatsmanInfoAdapterBinding
import com.example.cricbuzz_santosh.databinding.FragmentBowlerInfoAdapterBinding
import com.example.cricbuzz_santosh.databinding.FragmentBowlerInfoBinding


class BowlerInfoAdapter : RecyclerView.Adapter<BowlerInfoAdapter.ViewHolder>() {

    private var list= mutableListOf<Player>()

    class ViewHolder(val binding: FragmentBowlerInfoAdapterBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return (ViewHolder(
            FragmentBowlerInfoAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.ivPlayerImageHome.setImageBitmap(BitmapFactory.decodeByteArray(list[position].profilePhoto,0,list[position].profilePhoto!!.size))
        holder.binding.tvPlayerName.text = list[position].name
        holder.binding.tvPlayerCountry.text = list[position].country

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updatePlayerList(list1:MutableList<Player>){
        list.clear()
        for (i in list1){
            if(i.style=="Bowler")
                list.add(i)
        }
        notifyDataSetChanged()

    }

}