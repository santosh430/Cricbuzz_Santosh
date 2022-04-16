package com.example.cricbuzz_santosh.presentation.ui.playerprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player

class BattingFragment(val player: Player) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_batting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tv_matches).text= player.matches.toString()
        view.findViewById<TextView>(R.id.tv_runs).text= player.runs.toString()

    }


}