package com.baidu.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide




class RecyclerViewAdapter_1(val textList:List<Text>):
    RecyclerView.Adapter<RecyclerViewAdapter_1.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // 声明监听器
    var onItemClickListener: OnItemClickListener? = null



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textDemo1: TextView = view.findViewById(R.id.text1)
        val textDemo2: TextView = view.findViewById(R.id.text2)
        val textDemo3: TextView = view.findViewById(R.id.text3)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // 将点击事件通知给监听器
                    onItemClickListener?.onItemClick(position)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_item1,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val textDemo = textList[position]
        holder.textDemo1.text = textDemo.text1
        holder.textDemo2.text = textDemo.text2
        holder.textDemo3.text = textDemo.text3
    }

    override fun getItemCount() = textList.size

    fun getTextAtPosition(position: Int): Text? {
        if (position in 0 until textList.size) {
            return textList[position]
        }
        return null
    }
}

//have picture
class RecyclerViewAdapter_2(val textList2:List<TextImage>) :
    RecyclerView.Adapter<RecyclerViewAdapter_2.ViewHolder2>(){
        inner class ViewHolder2(view: View) : RecyclerView.ViewHolder(view){
            val l2TextDemo1: TextView = view.findViewById(R.id.l2_text1)
            val l2TextDemo2: TextView = view.findViewById(R.id.l2_text2)
            val l2TextDemo3: TextView = view.findViewById(R.id.l2_text3)
            val l2TextImage: ImageView = view.findViewById((R.id.l2_image1))
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycleview_item2,parent,false)
            return ViewHolder2(view)
        }

        override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
            val textDemo2 = textList2[position]
            holder.l2TextDemo1.text = textDemo2.text1
            holder.l2TextDemo2.text = textDemo2.text2
            holder.l2TextDemo3.text = textDemo2.text3
            holder.l2TextImage.setImageResource(textDemo2.imageId)
        }

        override fun getItemCount() = textList2.size
    }

//add news
class RecyclerViewAdapter_3(val textList3:List<NewsItem>) :
    RecyclerView.Adapter<RecyclerViewAdapter_3.ViewHolder3>() {

        interface OnItemClickListener {
            fun onItemClick(position: Int)
        }

        // 声明监听器
        var onItemClickListener: OnItemClickListener? = null

    inner class ViewHolder3(view: View) : RecyclerView.ViewHolder(view) {
        val l3TextDemo1: TextView = view.findViewById(R.id.l2_text1)
        val l3TextDemo2: TextView = view.findViewById(R.id.l2_text2)
        val l3TextDemo3: TextView = view.findViewById(R.id.l2_text3)
        val l3TextImage: ImageView = view.findViewById((R.id.l2_image1))

        fun bind(imageUrl: String) {
            // 使用 Glide 加载图片
            Glide.with(itemView)
                .load(imageUrl) // 传入图片的 URL 或者字符串格式的图片地址
                .placeholder(R.drawable.loading) // 设置加载过程中的占位图
                .error(R.drawable.anim_1) // 设置加载错误时显示的图片
                .into(l3TextImage) // 将图片加载到 l3TextImage 这个 ImageView 中
        }
        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // 将点击事件通知给监听器
                    onItemClickListener?.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder3 {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_item2, parent, false)
        return ViewHolder3(view)
    }

    override fun getItemCount() = textList3.size

    override fun onBindViewHolder(holder: ViewHolder3, position: Int) {
        val textDemo3 = textList3[position]
        val data = StringBuilder()
            .append(textDemo3.title)
            .append("\n")
            .append(textDemo3.summary)
            .toString()
        holder.l3TextDemo1.text = data
        holder.l3TextDemo2.text = "热点"
        holder.l3TextDemo3.text = "热点消息"
        holder.bind(textDemo3.imageUrl)
    }
    fun getTextAtPosition(position: Int): NewsItem? {
        if (position in 0 until textList3.size) {
            return textList3[position]
        }
        return null
    }

}

