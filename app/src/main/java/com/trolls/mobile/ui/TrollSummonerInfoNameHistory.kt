package com.trolls.mobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trolls.mobile.databinding.FragmentTrollSummonerInfoNameHistoryBinding

class TrollSummonerInfoNameHistory : Fragment() {
    private var _binding: FragmentTrollSummonerInfoNameHistoryBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TrollSummonerInfoNameHistory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTrollSummonerInfoNameHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}
