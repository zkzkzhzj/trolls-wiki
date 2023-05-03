package com.trolls.mobile.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.trolls.mobile.R
import com.trolls.mobile.adapter.LatelySearchTrollSummonerAdapter
import com.trolls.mobile.adapter.LiveTrollSummonerCommentAdapter
import com.trolls.mobile.data.DataSource
import com.trolls.mobile.databinding.FragmentMainBinding
import com.trolls.mobile.domain.LatelySearchType
import com.trolls.mobile.domain.RegisterType
import com.trolls.mobile.repository.PrefRepository
import com.trolls.mobile.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var behavior: BottomSheetBehavior<LinearLayoutCompat>

    private val registerView = "null"
    private val failView = "fail"
    private val summonerView = "success"

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCommentRecyclerView()
        setLatelySearchView()
        setBottomSheetBehavior()

        binding.topBar.topBarMenu.setOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            val handled = when (menuItem.itemId) {
                R.id.menu_setting -> {
                    Log.i(MainViewModel.LOG_TITLE, "setting")
                    true
                }
                R.id.menu_notice -> {
                    Log.i(MainViewModel.LOG_TITLE, "notice")
                    true
                }
                else -> false
            }
            if (handled) {
                binding.drawerLayout.closeDrawers()
            }
            handled
        }

        binding.themeModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setThemeMode(isChecked)
        }

        binding.latelySearchNull.latelySearchNullButton.setOnClickListener {
            searchViewFocus(binding.searchBar)
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(search: String?): Boolean {
                when (search) {
                    summonerView -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.troll_summoner_info_fragment, TrollSummonerInfo.newInstance())
                            .commitNow()
                        showBottomSheet()
                    }
                    failView -> {
                        val dialog = AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.not_found_summoner_dialog_title))
                            .setMessage(getString(R.string.not_found_summoner_dialog_message))
                            .setPositiveButton(getString(R.string.not_found_summoner_dialog_button)) { _, _ ->
                                searchViewFocus(binding.searchBar)
                            }
                            .create()
                        dialog.show()

                        childFragmentManager.beginTransaction()
                            .replace(R.id.troll_summoner_info_fragment, Fragment())
                            .commitNow()
                        hideBottomSheet()
                    }
                    registerView -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.troll_summoner_info_fragment, TrollSummonerRegister.newInstance(RegisterType.REGISTER))
                            .commitNow()
                        showBottomSheet()
                    }
                }

                binding.searchBar.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private fun searchViewFocus(searchView: SearchView) {
        searchView.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.searchBar.findFocus(), InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setBottomSheetBehavior() {
        behavior = BottomSheetBehavior.from(binding.trollSummonerInfo.trollSummonerInfoLayout)
        behavior.apply {
            isHideable = true
            peekHeight = viewModel.getBottomSheetMinHeight(getScreenHeight())
            maxHeight = viewModel.getBottomSheetMaxHeight(getScreenHeight())
        }
        hideBottomSheet()
    }

    private fun showBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setLatelySearchView() {
        if (PrefRepository(requireContext()).getLatelySearch().isEmpty()) {
            changeLatelySearchView(LatelySearchType.NULL)
            return
        }
        changeLatelySearchView(LatelySearchType.NOT_NULL)

        val latelySearchTrollSummonerAdapter = LatelySearchTrollSummonerAdapter()
        binding.latelySearchTrollSummoner.latelySearchTrollSummonerRecycler.apply {
            adapter = latelySearchTrollSummonerAdapter
            addItemDecoration(LatelySearchTrollSummonerAdapter.Decoration(16))
        }
        latelySearchTrollSummonerAdapter.submitList(DataSource().loadTrollSummonerData())
    }

    private fun changeLatelySearchView(type: LatelySearchType) {
        when (type) {
            LatelySearchType.NOT_NULL -> {
                binding.latelySearchNull.root.visibility = View.INVISIBLE
                binding.latelySearchTrollSummoner.root.visibility = View.VISIBLE
            }
            LatelySearchType.NULL -> {
                binding.latelySearchNull.root.visibility = View.VISIBLE
                binding.latelySearchTrollSummoner.root.visibility = View.INVISIBLE
            }
        }
    }

    private fun setCommentRecyclerView() {
        val liveTrollSummonerCommentAdapter = LiveTrollSummonerCommentAdapter()
        binding.liveTrollSummonerComment.liveTrollSummonerCommentRecycler.apply {
            adapter = liveTrollSummonerCommentAdapter
            addItemDecoration(LiveTrollSummonerCommentAdapter.Decoration(16))
        }
        liveTrollSummonerCommentAdapter.submitList(DataSource().loadTrollCommentData())
    }

    private fun getScreenHeight(): Int =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            requireActivity().windowManager.currentWindowMetrics.bounds.height()
        } else {
            requireActivity().resources.displayMetrics.heightPixels
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
