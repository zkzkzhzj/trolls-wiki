package com.trolls.mobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trolls.mobile.databinding.FragmentTrollSummonerInfoGameHistoryBinding

class TrollSummonerInfoGameHistory : Fragment() {
    private var _binding: FragmentTrollSummonerInfoGameHistoryBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TrollSummonerInfoGameHistory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTrollSummonerInfoGameHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}
