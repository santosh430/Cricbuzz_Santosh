package com.example.cricbuzz_santosh.presentation.ui.playerprofile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricbuzz_santosh.data.dbhelper.Player

class AdapterViewPagerPlayerProfile(
    val player: Player,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return   when(position){
            0->{
                InfoFragment(player)
            }
            1->{
                BattingFragment(player)
            }
            2->{
                BowlingFragment(player)
            }
            else->{
                Fragment()
            }

        }
    }
}