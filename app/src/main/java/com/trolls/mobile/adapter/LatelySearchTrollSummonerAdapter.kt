package com.trolls.mobile.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.trolls.mobile.R
import com.trolls.mobile.domain.TrollSummoner

class LatelySearchTrollSummonerAdapter :
    ListAdapter<TrollSummoner, LatelySearchTrollSummonerAdapter.ViewHolder>(diffUtil) {

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
        private val image = view.findViewById<ImageView>(R.id.troll_summoner_image)
        private val name = view.findViewById<TextView>(R.id.troll_summoner_name)
        private val titleRecyclerView = view.findViewById<RecyclerView>(R.id.troll_summoner_title_recycler)
        private val icon = view.findViewById<ImageView>(R.id.troll_summoner_icon)
        private val trollSummonerTitleAdapter = TrollSummonerTitleAdapter()

        fun bind(trollSummoner: TrollSummoner) {
            Glide.with(itemView.context)
                .load(trollSummoner.image)
                .override(200, 200)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
                .into(image)
            Glide.with(itemView.context)
                .load(trollSummoner.icon)
                .override(200, 200)
                .into(icon)

            name.text = trollSummoner.name

            titleRecyclerView.apply {
                adapter = trollSummonerTitleAdapter
                addItemDecoration(TrollSummonerTitleAdapter.Decoration(16))
            }
            trollSummonerTitleAdapter.submitList(trollSummoner.title.toList())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.lately_search_troll_summoner_recycler_item, parent, false),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TrollSummoner>() {
            override fun areItemsTheSame(
                oldItem: TrollSummoner,
                newItem: TrollSummoner,
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: TrollSummoner,
                newItem: TrollSummoner,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
