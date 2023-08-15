package com.baidu.app

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide



class NewsActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_text)

        val titleDemo = findViewById<TextView>(R.id.titleText)
        val bodyDemo = findViewById<TextView>(R.id.bodyText)
        val newsImageDemo = findViewById<ImageView>(R.id.newsImage)

        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")
        val newsImage = intent.getStringExtra("newsImage")

        Log.d("NewsActivity", "Title: $title, Body: $body,newsImage: $newsImage")

        titleDemo.text = title
        bodyDemo.text = body
//        newsImageDemo.setImageURI(newsImage)
        Glide.with(this)
            .load(newsImage)
            .error(R.drawable.anim_1)
            .into(newsImageDemo)
    }
}