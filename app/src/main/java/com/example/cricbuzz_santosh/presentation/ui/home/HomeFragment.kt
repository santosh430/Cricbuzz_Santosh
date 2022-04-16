package com.example.cricbuzz_santosh.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.data.dbhelper.PlayerDataBase
import com.example.cricbuzz_santosh.data.repo.PlayerRepository
import com.example.cricbuzz_santosh.databinding.AdapterHomeRecyclerBinding
import com.example.cricbuzz_santosh.databinding.FragmentHomeBinding
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModel
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModelFactory
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var vm:MainViewModel
    private lateinit var repository:PlayerRepository
    private lateinit var dataBase:PlayerDataBase
    private lateinit var dataBinding: FragmentHomeBinding
    private lateinit var myadapter:HomeRecylerAdapter
    private lateinit var toggle:ActionBarDrawerToggle

    var list = mutableListOf<Player>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentHomeBinding.inflate(inflater)
        repository = PlayerRepository(PlayerDataBase.getDataBase(requireActivity().applicationContext))
        dataBase = PlayerDataBase.getDataBase(requireContext().applicationContext)
        vm = ViewModelProvider(this, MainViewModelFactory(repository,dataBase)).get(MainViewModel::class.java)
        dataBinding = FragmentHomeBinding.inflate(inflater)


        //setting recycler view adapter

        dataBinding.rvHomeFragment.apply {
            layoutManager=LinearLayoutManager(context)
            myadapter = HomeRecylerAdapter(this@HomeFragment)
            adapter=myadapter
        }

        navigationBar()  //setting navigation bar

        vm.player.observe(viewLifecycleOwner){

            if (it.isEmpty())
                dataBinding.tvNoDataFound.isVisible=true
            else {
                list.addAll(it)
                dataBinding.tvNoDataFound.isInvisible = true
                dataBinding.rvHomeFragment.isVisible = true
                myadapter.updatePlayerList(it as MutableList<Player>)
                Log.d("TAG",it.toString())
                dataBinding.rvHomeFragment.adapter?.notifyDataSetChanged()
            }

        }

        // Inflate the layout for this fragment
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.ivAddFragmentHome.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addPlayerFragment)
        }

        dataBinding.ivMenuFragmentHome.setOnClickListener {
            dataBinding.drawerLayout.openDrawer(Gravity.LEFT)
        }



        dataBinding.svHomeFragment.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                var mList= mutableListOf<Player>()
                var found=false

                if (newText?.length!! >=3) {
                    Log.d("newText",newText)
                    Log.d("stored list",list.toString())
                    for(i in list) {
                        if (i.name!!.contains(newText.lowercase(Locale.getDefault()),true)) {
                            found=true
                            mList.add(i)
                            Log.d("inside for each ", i.name.toString())
                            myadapter.updatePlayerList(mList)
                            dataBinding.rvHomeFragment.adapter?.notifyDataSetChanged()
                        }

                    }

                }

              /*  else
                {
                    if(newText?.length>=0) {
                            Log.d("myTAG","stored list $list")
                            myadapter.updatePlayerList(list)
                            dataBinding.rvHomeFragment.adapter?.notifyDataSetChanged()
                    }
                }
*/
            return true
            }

        })

    }

    private fun navigationBar() {

        val drawLayout: DrawerLayout =dataBinding.drawerLayout
        val nView: NavigationView =dataBinding.navView
        toggle = ActionBarDrawerToggle(activity,drawLayout,R.string.open,R.string.close)

        dataBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.nav_home-> dataBinding.drawerLayout.closeDrawer(Gravity.LEFT)

                R.id.nav_my_fav->{
                    Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_favouritePlayerFragment)
                }


                R.id.nav_batsman->Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_batsmanInfoFragment)

                R.id.nav_bowler->Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_bowlerInfoFragment)
            }

            true
        }

    }


}