package com.example.cricbuzz_santosh.presentation.ui.playerprofile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.data.dbhelper.PlayerDataBase
import com.example.cricbuzz_santosh.data.repo.PlayerRepository
import com.example.cricbuzz_santosh.databinding.FragmentPlayerProfileBinding
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModel
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator


class PlayerProfileFragment(val player: Player) : Fragment() {

    var count=0
    private lateinit var vm:MainViewModel
    private lateinit var repository:PlayerRepository
    private lateinit var dataBase:PlayerDataBase
    private lateinit var dataBinding:FragmentPlayerProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentPlayerProfileBinding.inflate(inflater)
        repository = PlayerRepository(PlayerDataBase.getDataBase(requireActivity().applicationContext))
        dataBase = PlayerDataBase.getDataBase(requireContext().applicationContext)
        vm = ViewModelProvider(this, MainViewModelFactory(repository,dataBase)).get(MainViewModel::class.java)

        Log.d("OncreateView"," created $count times")
        count++


        //initializing viewpager


        val adapter=AdapterViewPagerPlayerProfile(player,requireActivity().supportFragmentManager,lifecycle)

        dataBinding.vpPlayerProfile.adapter=adapter

        TabLayoutMediator(dataBinding.tabLayout,dataBinding.vpPlayerProfile){tab,position->
            when(position){
                0->{
                    tab.text="Info"
                }
                1->{
                    tab.text="Batting"
                }
                2->{
                    tab.text="Bowling"
                }
            }
        }.attach()

        //setting fav icon
        if(player.fav!! == 1){
            dataBinding.ivFavStar.isVisible=true
            dataBinding.ivNotFavStar.isVisible=false
        }
        else
        {
            dataBinding.ivNotFavStar.isVisible=true
            dataBinding.ivFavStar.isVisible=false
        }

        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Player Profile",player.toString())


//        dataBinding.ivBackArrow.setOnClickListener {
//            view.findNavController().popBackStack()
//            if(view.findNavController().currentDestination?.id==R.id.playerProfile)
//                view.findNavController().navigate(R.id.action_playerProfileFragment_to_homeFragment)
//            else {
//             //   view.findNavController().popBackStack()
//                Log.d("tgtgt", view.findNavController().currentDestination.toString())
////                view.findNavController().navigate(R.id.action_playerProfileFragment_to_homeFragment)
//            }


        // updating user fav player
        dataBinding.ivNotFavStar.setOnClickListener {
            vm.updateFavPlayer(player.name.toString(),1)
            dataBinding.ivNotFavStar.isVisible=false
            dataBinding.ivFavStar.isVisible=true
            Toast.makeText(context, "${player.name} added to Favourite", Toast.LENGTH_SHORT).show()

        }
        //removing favourite player
        dataBinding.ivFavStar.setOnClickListener {
            vm.updateFavPlayer(player.name.toString(),0)
            dataBinding.ivNotFavStar.isVisible=true
            dataBinding.ivFavStar.isVisible=false
            Toast.makeText(context, "${player.name} removed from Favourite", Toast.LENGTH_SHORT).show()

        }

        dataBinding.playerName.text=player.name
//        dataBinding.ivBackArrow.setOnClickListener {
////            Navigation.findNavController(requireView()).navigate(R.id.action_playerProfileFragment2_to_homeFragment)
//
//        }

    }

}