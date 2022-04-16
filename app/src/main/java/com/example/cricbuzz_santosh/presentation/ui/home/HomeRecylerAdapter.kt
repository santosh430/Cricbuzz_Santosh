package com.example.cricbuzz_santosh.presentation.ui.home

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.databinding.AdapterHomeRecyclerBinding
import com.example.cricbuzz_santosh.presentation.ui.playerprofile.PlayerProfileFragment

class HomeRecylerAdapter(private val homeFragment: HomeFragment) : RecyclerView.Adapter<HomeRecylerAdapter.ViewHolder>() {

    var list= mutableListOf<Player>()

    inner class ViewHolder(val binding: AdapterHomeRecyclerBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return (ViewHolder(
            AdapterHomeRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
                 parent, false)
            ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ivPlayerImageHome.setImageBitmap(BitmapFactory.decodeByteArray(list[position].profilePhoto,0,list[position].profilePhoto!!.size))
        holder.binding.tvPlayerName.text=list[position].name
        holder.binding.tvPlayerCountry.text=list[position].country


        holder.itemView.setOnClickListener {

            Log.d("HomeRecyclerAdapter","Click Listener working")
            homeFragment.activity?.supportFragmentManager
                ?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.drawerLayout,PlayerProfileFragment(list[position]))?.commit()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun updatePlayerList(list1:MutableList<Player>){
        list.clear()
        list=list1
        Log.d("myTAG","list in adapter $list1")
        notifyDataSetChanged()

    }
}