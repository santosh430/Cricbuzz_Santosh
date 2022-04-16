package com.example.cricbuzz_santosh.presentation.ui.playerprofile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.data.dbhelper.PlayerDataBase
import com.example.cricbuzz_santosh.data.repo.PlayerRepository
import com.example.cricbuzz_santosh.databinding.FragmentInfoBinding
import com.example.cricbuzz_santosh.databinding.FragmentPlayerProfileBinding
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModel
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModelFactory


class InfoFragment(val player: Player) : Fragment() {

    private lateinit var vm:MainViewModel
    private lateinit var repository:PlayerRepository
    private lateinit var dataBase:PlayerDataBase
    private lateinit var dataBinding:FragmentInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentInfoBinding.inflate(inflater)
        repository = PlayerRepository(PlayerDataBase.getDataBase(requireActivity().applicationContext))
        dataBase = PlayerDataBase.getDataBase(requireContext().applicationContext)
        vm = ViewModelProvider(this, MainViewModelFactory(repository,dataBase)).get(MainViewModel::class.java)

        // Inflate the layout for this fragment
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.ivProfilePictureInfoFragment.setImageBitmap(BitmapFactory.decodeByteArray(player.profilePhoto,0,player.profilePhoto!!.size))
        dataBinding.tvNickname.text=player.name
        dataBinding.tvStyleInfoFragment.text=player.style
        dataBinding.tvPlayerNameInfoFragment.text=player.name
        dataBinding.tvPlayerCountryNameInfoFragment.text=player.country
        dataBinding.tvBorn.text=player.dob
        dataBinding.tvTeamInfoFragment.text=player.team

        Log.d("InfoFragment",player.toString())

    }


}