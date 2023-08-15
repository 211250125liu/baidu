package com.baidu.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoAdapter(val VideoItemList:List<VideoItem>,private val clickListener: OnItemClickListener):
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

        interface OnItemClickListener{
            fun onItemClick(position: Int)
        }
        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
            val videoTitle : TextView = view.findViewById(R.id.this_videoTitle)
            val videoImage : ImageView = view.findViewById(R.id.videoImage)

            init {
                view.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        clickListener.onItemClick(position)
                    }
                }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val textDemo = VideoItemList[position]
        holder.videoTitle.text = textDemo.videoTitle
        Glide.with(holder.itemView.context)
            .load(textDemo.videoImage)
            .into(holder.videoImage)
    }

    override fun getItemCount() = VideoItemList.size


}
