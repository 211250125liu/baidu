package com.baidu.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class weatherRecycler  (val weatherList : List<Weather>):
        RecyclerView.Adapter<weatherRecycler.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val thisText : TextView = view.findViewById(R.id.text)
        val textImage : ImageView = view.findViewById(R.id.img1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_recycle,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val textDemo = weatherList[position]
        holder.thisText.text = textDemo.text
        holder.textImage.setImageResource(textDemo.imageId)
    }

    override fun getItemCount() = weatherList.size
}

