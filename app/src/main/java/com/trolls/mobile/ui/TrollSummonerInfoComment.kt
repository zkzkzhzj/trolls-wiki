package com.trolls.mobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trolls.mobile.adapter.LiveTrollSummonerCommentAdapter
import com.trolls.mobile.data.DataSource
import com.trolls.mobile.databinding.FragmentTrollSummonerInfoCommentBinding

class TrollSummonerInfoComment : Fragment() {
    private var _binding: FragmentTrollSummonerInfoCommentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TrollSummonerInfoComment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTrollSummonerInfoCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setCommentRecycler()
    }

    private fun setCommentRecycler() {
        val liveTrollSummonerCommentAdapter = LiveTrollSummonerCommentAdapter()
        binding.trollSummonerInfoCommentRecycler.apply {
            adapter = liveTrollSummonerCommentAdapter
            addItemDecoration(LiveTrollSummonerCommentAdapter.Decoration(16))
            setOnTouchListener { v, event ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                v.onTouchEvent(event)
                v.performClick()
            }

        }
        liveTrollSummonerCommentAdapter.submitList(DataSource().loadTrollCommentData())
    }
}
