package com.baidu.app

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity(){

    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vedio)

        videoView = findViewById(R.id.video_player)

        mediaController = MediaController(this)
        //val videoUrl = "https://globalimg.sucai999.com/uploadfile/20211210/267440/132836000096069452.mp4"
        val videoUrl = intent.getStringExtra("videoUrl")
        videoView.setVideoURI(Uri.parse(videoUrl))
        videoView.setMediaController(mediaController)
        videoView.start()
    }

}
