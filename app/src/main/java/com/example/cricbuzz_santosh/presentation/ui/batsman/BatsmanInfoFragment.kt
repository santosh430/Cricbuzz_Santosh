package com.example.cricbuzz_santosh.presentation.ui.batsman

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.cricbuzz_santosh.databinding.FragmentFavouritePlayerBinding
import com.example.cricbuzz_santosh.presentation.ui.fav.FavPlayerAdapter
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModel
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModelFactory

class BatsmanInfoFragment : Fragment() {

    private lateinit var vm: MainViewModel
    private lateinit var repository: PlayerRepository
    private lateinit var dataBase: PlayerDataBase
    private lateinit var dataBinding: FragmentBatsmanInfoBinding
    private lateinit var myadapter: BatsmanInfoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentBatsmanInfoBinding.inflate(inflater)
        repository = PlayerRepository(PlayerDataBase.getDataBase(requireActivity().applicationContext))
        dataBase = PlayerDataBase.getDataBase(requireContext().applicationContext)
        vm = ViewModelProvider(this, MainViewModelFactory(repository,dataBase)).get(MainViewModel::class.java)

        dataBinding.rvBatsman.apply {
            layoutManager= LinearLayoutManager(context)
            myadapter = BatsmanInfoAdapter()
            adapter=myadapter
        }

        // Inflate the layout for this fragment
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.player.observe(viewLifecycleOwner){

            var visible=true

            for(i in it) {
                if (i.style == "Batsman")
                    visible = false
            }
            if (visible)
                dataBinding.tvNoFavPlayerFound.isVisible=true
            else
                dataBinding.tvNoFavPlayerFound.isInvisible=true

            myadapter.updatePlayerList(it as MutableList<Player>)


            dataBinding.ivBackBatsman.setOnClickListener {
                findNavController().popBackStack()
//                Navigation.findNavController(requireView()).navigate(R.id.action_batsmanInfoFragment_to_homeFragment)
            }
        }

    }
}