package com.example.cricbuzz_santosh.presentation.ui.playerprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player

class BowlingFragment(val player: Player) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bowling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tv_matchesFragmentBowling).text=player.matches.toString()
        view.findViewById<TextView>(R.id.tv_runsFragmentBowling).text=player.runs.toString()
        view.findViewById<TextView>(R.id.tv_wicketsFragmentBowling).text=player.wickets.toString()


    }


}