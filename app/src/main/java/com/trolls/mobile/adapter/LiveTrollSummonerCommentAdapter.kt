package com.trolls.mobile.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trolls.mobile.R
import com.trolls.mobile.domain.LiveComment

class LiveTrollSummonerCommentAdapter :
    ListAdapter<LiveComment, LiveTrollSummonerCommentAdapter.ViewHolder>(diffUtil) {

    class Decoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            val position = parent.getChildLayoutPosition(view)

            if (position != 0) {
                outRect.top = space
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.live_troll_summoner_comment_name)
        private val time = view.findViewById<TextView>(R.id.live_troll_summoner_comment_time)
        private val body = view.findViewById<TextView>(R.id.live_troll_summoner_comment_body)

        fun bind(liveComment: LiveComment) {
            name.text = liveComment.name
            body.text = liveComment.body
            time.text = "${liveComment.createAt}분전"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.live_troll_summoner_comment_recycler_item, parent, false),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<LiveComment>() {
            override fun areItemsTheSame(oldItem: LiveComment, newItem: LiveComment): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: LiveComment, newItem: LiveComment): Boolean {
                return oldItem == newItem
            }
        }
    }
}
