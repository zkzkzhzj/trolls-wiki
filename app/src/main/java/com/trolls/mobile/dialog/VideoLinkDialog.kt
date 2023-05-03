package com.trolls.mobile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.trolls.mobile.databinding.TrollRegisterVideoLinkInputDialogBinding

class VideoLinkDialog : DialogFragment() {
    interface OnVideoLinkReceiveListener {
        fun onVideoLinkReceive(videoLink: String)
    }

    private var mOnVideoLinkReceiveListener: OnVideoLinkReceiveListener? = null

    fun setOnVideoLinkReceiveListener(listener: OnVideoLinkReceiveListener) {
        mOnVideoLinkReceiveListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = false
    }

    private var _binding: TrollRegisterVideoLinkInputDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = TrollRegisterVideoLinkInputDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.trollRegisterVideoLinkAddBtn.setOnClickListener {
            if (binding.trollRegisterVideoLinkEdit.text.toString() != "") {
                mOnVideoLinkReceiveListener?.onVideoLinkReceive(
                    binding.trollRegisterVideoLinkEdit.text.toString(),
                )
            }
            dismiss()
        }

        binding.trollRegisterVideoLinkCloseBtn.setOnClickListener {
            dismiss()
        }
    }
}
