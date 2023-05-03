package com.trolls.mobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.trolls.mobile.R
import com.trolls.mobile.adapter.TrollSummonerTitleAdapter
import com.trolls.mobile.data.DataSource
import com.trolls.mobile.databinding.FragmentTrollSummonerInfoBinding
import com.trolls.mobile.domain.RegisterType
import com.trolls.mobile.domain.TrollSummoner

class TrollSummonerInfo : Fragment() {
    private var _binding: FragmentTrollSummonerInfoBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TrollSummonerInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTrollSummonerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSummonerInfo(DataSource().loadTrollSummonerData()[0])
        setMenuItemClick()
    }

    private fun setSummonerInfo(trollSummoner: TrollSummoner) {
        val trollSummonerTitleAdapter = TrollSummonerTitleAdapter()

        Glide.with(requireContext())
            .load(trollSummoner.image)
            .override(200, 200)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .into(binding.latelySearchTrollSummonerInfo.trollSummonerImage)
        Glide.with(requireContext())
            .load(trollSummoner.icon)
            .override(200, 200)
            .into(binding.latelySearchTrollSummonerInfo.trollSummonerIcon)

        binding.latelySearchTrollSummonerInfo.trollSummonerName.text = trollSummoner.name

        binding.latelySearchTrollSummonerInfo.trollSummonerTitleRecycler.apply {
            adapter = trollSummonerTitleAdapter
            addItemDecoration(TrollSummonerTitleAdapter.Decoration(16))
        }

        trollSummonerTitleAdapter.submitList(trollSummoner.title.toList())
    }

    private fun setMenuItemClick() {
        binding.apply {
            setChildFragment(TrollSummonerInfoComment.newInstance())

            trollSummonerInfoMenuComment.setOnClickListener {
                menuItemEvent(this.trollSummonerInfoMenuComment)
            }
            trollSummonerInfoMenuReason.setOnClickListener {
                menuItemEvent(this.trollSummonerInfoMenuReason)
            }
            trollSummonerInfoMenuNameHistory.setOnClickListener {
                menuItemEvent(this.trollSummonerInfoMenuNameHistory)
            }
            trollSummonerInfoMenuGameHistory.setOnClickListener {
                menuItemEvent(this.trollSummonerInfoMenuGameHistory)
            }
        }
    }

    private fun menuItemEvent(textView: AppCompatTextView) {
        binding.trollSummonerInfoMenuComment.alpha = 0.5f
        binding.trollSummonerInfoMenuReason.alpha = 0.5f
        binding.trollSummonerInfoMenuNameHistory.alpha = 0.5f
        binding.trollSummonerInfoMenuGameHistory.alpha = 0.5f

        when (textView) {
            binding.trollSummonerInfoMenuComment -> {
                binding.trollSummonerInfoMenuComment.alpha = 1f
                setChildFragment(TrollSummonerInfoComment.newInstance())
            }
            binding.trollSummonerInfoMenuReason -> {
                binding.trollSummonerInfoMenuReason.alpha = 1f
                setChildFragment(TrollSummonerRegister.newInstance(RegisterType.VIEWER))
            }
            binding.trollSummonerInfoMenuNameHistory -> {
                binding.trollSummonerInfoMenuNameHistory.alpha = 1f
                setChildFragment(TrollSummonerInfoNameHistory.newInstance())
            }
            binding.trollSummonerInfoMenuGameHistory -> {
                binding.trollSummonerInfoMenuGameHistory.alpha = 1f
                setChildFragment(TrollSummonerInfoGameHistory.newInstance())
            }
        }
    }

    private fun setChildFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.troll_summoner_info_menu_fragment, fragment)
            .commitNow()
    }
}
