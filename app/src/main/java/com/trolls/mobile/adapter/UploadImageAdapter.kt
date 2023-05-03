package com.trolls.mobile.adapter

import android.graphics.Rect
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trolls.mobile.R

class UploadImageAdapter : ListAdapter<Uri, UploadImageAdapter.ViewHolder>(diffUtil) {
    interface OnDisableClickListener {
        fun onDisableClick(position: Int)
    }

    private var mOnDisableClickListener: OnDisableClickListener? = null

    fun setOnDisableClickListener(listener: OnDisableClickListener) {
        mOnDisableClickListener = listener
    }

    class Decoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            val position = parent.getChildLayoutPosition(view)
            
            if (position != 0) {
                outRect.left = space
            }
        }
    }
    
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.upload_image_recycler_image)

        fun bind(item: Uri) {
            Glide.with(itemView.context)
                .load(item)
                .override(400, 400)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.upload_image_recycler_item, parent, false),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.findViewById<ImageView>(R.id.upload_image_recycler_image_disable).setOnClickListener {
            mOnDisableClickListener?.onDisableClick(holder.absoluteAdapterPosition)
        }
    }
    
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem.path == newItem.path
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }
        }
    }
}
