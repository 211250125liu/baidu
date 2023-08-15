package com.baidu.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class VideoItem(
    val videoTitle : String,
    val videoImage: String,
    val videoUrl : String
)
class VideoFragment : Fragment(),VideoAdapter.OnItemClickListener{

    private val videoItemList = ArrayList<VideoItem>()
    private lateinit var videoItemRecycler : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.video, container, false)
        initText()

        val videoItemManger = LinearLayoutManager(requireContext())
        videoItemRecycler = view.findViewById(R.id.videoRecyclerView)
        videoItemRecycler.layoutManager = videoItemManger
        videoItemRecycler.adapter = VideoAdapter(videoItemList,this)

        val videoSearch = view.findViewById<EditText>(R.id.videoSearchText)
        videoSearch.setOnClickListener{
            val videoSearchText = videoSearch.text.toString().trim()
            if(videoSearchText.isNotEmpty()){
                val searchUrl = "https://www.baidu.com/s?wd=$videoSearchText"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))
                try {
                    requireContext().startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return view
    }


    /**
     * 资源来源：https://blog.csdn.net/weixin_41010198/article/details/88055078
     * 博主：点亮~黑夜
     */
    private fun initText(){
        repeat(5){
            videoItemList.add(
                VideoItem("bird",
                    "https://img-blog.csdnimg.cn/20190301125102646.png",
                "https://yiqida.oss-cn-chengdu.aliyuncs.com/video2020-07-29/202007291754445.mp4")
            )
            videoItemList.add(
                VideoItem("fish",
                    "https://img-blog.csdnimg.cn/20190301125255914.png",
                    "https://globalimg.sucai999.com/uploadfile/20211210/267440/132836000096069452.mp4")
            )
            videoItemList.add(
                VideoItem("desert",
                    "https://img-blog.csdnimg.cn/20190301125528758.png",
                "https://media.w3.org/2010/05/sintel/trailer.mp4")
            )
        }
    }

    override fun onItemClick(position: Int) {
        val videoPreview = videoItemList[position]
        // 创建Intent并传递视频URL和封面URL到VideoPlayerActivity
        val intent = Intent(requireContext(), VideoActivity::class.java)
        intent.putExtra("videoUrl", videoPreview.videoUrl)
//        intent.putExtra("imageUrl", videoPreview.videoImage)
        startActivity(intent)
    }

}
