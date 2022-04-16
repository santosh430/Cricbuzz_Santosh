package com.example.cricbuzz_santosh.presentation.ui.bowler

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.data.dbhelper.PlayerDataBase
import com.example.cricbuzz_santosh.data.repo.PlayerRepository
import com.example.cricbuzz_santosh.databinding.FragmentBatsmanInfoBinding
import com.example.cricbuzz_santosh.databinding.FragmentBowlerInfoBinding
import com.example.cricbuzz_santosh.presentation.ui.batsman.BatsmanInfoAdapter
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModel
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModelFactory


class BowlerInfoFragment : Fragment() {

    private lateinit var vm: MainViewModel
    private lateinit var repository: PlayerRepository
    private lateinit var dataBase: PlayerDataBase
    private lateinit var dataBinding: FragmentBowlerInfoBinding
    private lateinit var myadapter: BowlerInfoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentBowlerInfoBinding.inflate(inflater)
        repository = PlayerRepository(PlayerDataBase.getDataBase(requireActivity().applicationContext))
        dataBase = PlayerDataBase.getDataBase(requireContext().applicationContext)
        vm = ViewModelProvider(this, MainViewModelFactory(repository,dataBase)).get(MainViewModel::class.java)

        dataBinding.rvBowler.apply {
            layoutManager= LinearLayoutManager(context)
            myadapter = BowlerInfoAdapter()
            adapter=myadapter
        }

        // Inflate the layout for this fragment
        return dataBinding.root
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bowler_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.player.observe(viewLifecycleOwner){
            var visible=true

            for(i in it) {
                if (i.style == "Bowler")
                    visible = false
            }
            if (visible)
                dataBinding.tvNoFavPlayerFound.isVisible=true
            else
                dataBinding.tvNoFavPlayerFound.isInvisible=true


            myadapter.updatePlayerList(it as MutableList<Player>)

        }

        dataBinding.ivBackBowler.setOnClickListener {
            findNavController().popBackStack()
//            Navigation.findNavController(requireView()).navigate(R.id.action_bowlerInfoFragment_to_homeFragment)
        }
    }
}