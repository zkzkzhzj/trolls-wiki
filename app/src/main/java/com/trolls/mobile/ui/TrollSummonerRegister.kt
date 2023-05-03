package com.trolls.mobile.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.trolls.mobile.adapter.UploadImageAdapter
import com.trolls.mobile.databinding.FragmentTrollSummonerRegisterBinding
import com.trolls.mobile.dialog.VideoLinkDialog
import com.trolls.mobile.domain.RegisterType
import com.trolls.mobile.viewmodel.MainViewModel

class TrollSummonerRegister : Fragment() {
    private var _binding: FragmentTrollSummonerRegisterBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_TYPE = android.Manifest.permission.READ_EXTERNAL_STORAGE

    private val uploadImageAdapter = UploadImageAdapter()

    private val videoLinkDialog = VideoLinkDialog()

    private lateinit var registerType: RegisterType

    private val checkPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        if (it) {
            getImage.launch("image/*")
            return@registerForActivityResult
        }

        if (!shouldShowRequestPermissionRationale(REQUEST_TYPE)) {
            Toast.makeText(requireContext(), "권한이 거부되어 이용하실수 없습니다.", Toast.LENGTH_LONG).show()
        }
    }

    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents(),
    ) { listUri ->
        if (listUri.isNotEmpty()) {
            uploadImageAdapter.submitList(listUri.toList())
        }
    }

    companion object {
        private const val TYPE = "RegisterType"

        fun newInstance(registerType: RegisterType) =
            TrollSummonerRegister().apply {
                arguments = Bundle().apply {
                    putSerializable(TYPE, registerType)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerType = arguments?.getSerializable(TYPE) as RegisterType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTrollSummonerRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (registerType) {
            RegisterType.REGISTER -> {
                Log.e(MainViewModel.LOG_TITLE, RegisterType.REGISTER.toString())
            }
            RegisterType.VIEWER -> {
                Log.e(MainViewModel.LOG_TITLE, RegisterType.VIEWER.toString())
            }
        }

        setRecyclerView()

        binding.trollSummonerRegisterImageIcon.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                checkPermission.launch(
                    REQUEST_TYPE,
                )
                return@setOnClickListener
            }
            
            getImage.launch("image/*")
        }

        binding.trollSummonerRegisterVideoIcon.setOnClickListener {
            videoLinkDialog.show(
                childFragmentManager,
                VideoLinkDialog::class.java.name,
            )
        }

        videoLinkDialog.setOnVideoLinkReceiveListener(object : VideoLinkDialog.OnVideoLinkReceiveListener {
            override fun onVideoLinkReceive(videoLink: String) {
                binding.trollSummonerRegisterVideoLink.text = videoLink
            }
        })
    }

    private fun setRecyclerView() {
        binding.trollSummonerRegisterImageRecycler.apply {
            adapter = uploadImageAdapter
            addItemDecoration(UploadImageAdapter.Decoration(16))
        }
        removeRecyclerItem()
    }

    private fun removeRecyclerItem() {
        uploadImageAdapter.setOnDisableClickListener(object : UploadImageAdapter.OnDisableClickListener {
            override fun onDisableClick(position: Int) {
                Log.e(MainViewModel.LOG_TITLE, "position: $position")
                val listUri = uploadImageAdapter.currentList.toMutableList()
                listUri.removeAt(position)
                uploadImageAdapter.submitList(listUri)
            }
        })
    }
}
