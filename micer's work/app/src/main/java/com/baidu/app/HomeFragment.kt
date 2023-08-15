package com.baidu.app

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baidu.app.news.NewsDBHelper


class Text(val text1:String, val text2:String, val text3:String)
class TextImage(val text1:String, val text2:String, val text3:String, val imageId: Int)
data class NewsItem(
    val id: Int,
    val title: String,
    val summary: String,
    val body: String,
    val imageUrl: String
    )

class HomeFragment : Fragment()  {
    private val LOADING_DELAY: Long = 500 // 150ms
    private val REQUEST_CODE = 101
    private lateinit var progressBar: ProgressBar
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var weatherText : TextView
    private lateinit var weatherNumberText : TextView
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home, container, false)
        progressBar = view.findViewById(R.id.progressBar1)

        //编辑框
        val searchText = view.findViewById<EditText>(R.id.SearchText)
//        searchText.setOnTouchListener{
//                v,event ->
//            v.parent.requestDisallowInterceptTouchEvent(true)
//            false
//        }

        searchText.setOnClickListener{
            val searchQuery = searchText.text.toString().trim()
            if (searchQuery.isNotEmpty()) {
                // Create a URL for the Baidu search page with the entered text
                val searchUrl = "https://www.baidu.com/s?wd=$searchQuery"
                //val searchUrl = "https://www.bing.com/search?q=$searchQuery"
                // Open the URL in a web browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))
                try {
                    requireContext().startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


        // 添加RecycleView
        initText1()
        val recycler1 = view.findViewById<RecyclerView>(R.id.recyclerView_text1)
        val layoutManager1 = LinearLayoutManager(requireContext())
        recycler1.layoutManager = layoutManager1
        val adapter1 = RecyclerViewAdapter_1(textList)

        // 设置Adapter的点击事件监听器
        adapter1.onItemClickListener = object : RecyclerViewAdapter_1.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val clickedText = adapter1.getTextAtPosition(position)
                if(clickedText != null){
                    val intent = Intent(requireActivity(), NewsActivity::class.java)
                    intent.putExtra("title", clickedText.text1)
                    intent.putExtra("body", clickedText.text3)
                    startActivity(intent)
                }
            }
        }

        recycler1.adapter = adapter1

//        initText2()
//        val recyclerView2 = view.findViewById<RecyclerView>(R.id.recyclerView_text2)
//        val layoutManager2 = LinearLayoutManager(requireContext())
//        recyclerView2.layoutManager = layoutManager2
//        val adapter2 = recyclerViewAdapter_2(textImageList)
//        recyclerView2.adapter = adapter2

        initText3()
        val recycler3 = view.findViewById<RecyclerView>(R.id.recyclerView_text2)
        val layoutManager3 = LinearLayoutManager(requireContext())
        recycler3.layoutManager = layoutManager3
        val adapter3 = RecyclerViewAdapter_3(NewsList)

        adapter3.onItemClickListener = object : RecyclerViewAdapter_3.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // 点击事件处理，启动 NewsActivity
                val clickedText = adapter3.getTextAtPosition(position)
                if(clickedText != null) {
                    val intent = Intent(requireActivity(), NewsActivity::class.java)
                    intent.putExtra("title",clickedText.title)
                    intent.putExtra("body", clickedText.body)
                    intent.putExtra("newsImage",clickedText.imageUrl)
                    startActivity(intent)
                }
            }
        }

        recycler3.adapter = adapter3

        weatherText = view.findViewById(R.id.textView3)
        weatherNumberText = view.findViewById(R.id.textView2)
        startAlphaAnimation()

        return view
        // 这里加载了名为my_fragment_layout.xml的布局文件
    }

    private fun startAlphaAnimation() {
        val alphaAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        alphaAnimator.duration = 2000 // 设置动画持续时间为2秒（从0到1的过程）
        alphaAnimator.interpolator = LinearInterpolator() // 设置动画插值器，线性插值器
        alphaAnimator.repeatCount = ValueAnimator.INFINITE // 设置重复次数为无限循环
        alphaAnimator.repeatMode = ValueAnimator.REVERSE // 设置重复模式为逆向（从1到0的过程）
        alphaAnimator.addUpdateListener { animation ->
            val alphaValue = animation.animatedValue as Float
            weatherText.alpha = alphaValue
        }
        alphaAnimator.start()
//        val alphaAnimator = ObjectAnimator.ofFloat(weatherText,"alpha",0.0f,1.0f)
//        alphaAnimator.duration = 1000 // 设置动画持续时间，这里是1秒
//        alphaAnimator.interpolator = DecelerateInterpolator() // 设置动画插值器，可以调整渐变速度
//        alphaAnimator.start()

        val alphaAnimator2 = ValueAnimator.ofFloat(0.0f, 1.0f)
        alphaAnimator2.duration = 2000 // 设置动画持续时间为2秒（从0到1的过程）
        alphaAnimator2.interpolator = LinearInterpolator() // 设置动画插值器，线性插值器
        alphaAnimator2.repeatCount = ValueAnimator.INFINITE // 设置重复次数为无限循环
        alphaAnimator2.repeatMode = ValueAnimator.REVERSE // 设置重复模式为逆向（从1到0的过程）
        alphaAnimator2.addUpdateListener { animation ->
            val alphaValue2 = animation.animatedValue as Float
            weatherNumberText.alpha = alphaValue2
        }
        alphaAnimator2.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather_view = view.findViewById<TextView>(R.id.textView3)
        val add_news_view = view.findViewById<ImageView>(R.id.add_news)

        // 在 onViewCreated 中设置点击事件
        weather_view.setOnClickListener {
            showLoading()
            handler.postDelayed({
                hideLoading()
                val intent = Intent(requireActivity(), WeatherActivity::class.java)
                startActivity(intent)
            }, LOADING_DELAY)
        }

        add_news_view.setOnClickListener {
            showLoading()
            handler.postDelayed({
                hideLoading()
                val intent = Intent(requireActivity(), AddNewsActivity::class.java)
                startActivity(intent)
            }, LOADING_DELAY)
        }
    }



    private val textList = ArrayList<Text>()
    private val textImageList = ArrayList<TextImage>()
    private val NewsList = ArrayList<NewsItem>()

    private fun initText1(){
        repeat(1){
            textList.add(Text("这些重大问题，习近平的回答掷地有声","置顶","新华社新媒体"))
            textList.add(Text("习近平会见”元老会“代表团","置顶", "央视网新闻"))
        }


    }
    private fun initText2(){
        repeat(1){
            textImageList.add(TextImage("美媒：五角大楼盯上谷歌在华AI中心 谷歌忙安抚","热点","热点消息",R.drawable.anim_1))
            textImageList.add(TextImage("蔡英文财产曝光：存款5406万 名下拥有6笔不动产","热点","热点消息", R.drawable.anim_2))
        }
    }

    private fun initText3(){
        repeat(2){
        NewsList.add(NewsItem(1,"美媒：五角大楼盯上谷歌在华AI中心 谷歌忙安抚","","",R.drawable.anim_1.toString()))
        NewsList.add(NewsItem(2,"蔡英文财产曝光：存款5406万 名下拥有6笔不动产","","", R.drawable.anim_2.toString()))
            }
        val dbHelper = NewsDBHelper(requireContext())
        // 以读取模式获取数据存储库
        val db: SQLiteDatabase = dbHelper.getReadableDatabase()
        val cursor = db.query("News", null, null, null, null, null, null)
        var i = 3
        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow("title"))
                val pictureUrl = getString(getColumnIndexOrThrow("pictureUrl"))
                val summary = getString(getColumnIndexOrThrow("summary"))
                val body = getString(getColumnIndexOrThrow("body"))
                Log.d("NewsActivity", "Title: $title, PictureUrl: $pictureUrl, Summary: $summary, Body: $body")
                NewsList.add(NewsItem(i,title,summary,body,pictureUrl))
                i++
            }
        }
        cursor.close()
    }

//    private fun initText2() {
//        // 创建一个用于存储 NewsItem 的列表
//        val newsList = mutableListOf<NewsItem>()
//
//        // 从数据库中查询数据并将查询结果添加到 newsList 中
//        val dbHelper = NewsDBHelper(requireContext())
//        val db = dbHelper.readableDatabase
//        val projection = arrayOf(NewsDBHelper.ID, NewsDBHelper.TITLE, NewsDBHelper.SUMMARY, NewsDBHelper.BODY, NewsDBHelper.IMAGE_URL)
//        val cursor = db.query(NewsDBHelper.TABLE_NAME, projection, null, null, null, null, null)
//        while (cursor.moveToNext()) {
//            val id = cursor.getInt(cursor.getColumnIndexOrThrow(NewsDBHelper.ID))
//            val title = cursor.getString(cursor.getColumnIndexOrThrow(NewsDBHelper.TITLE))
//            val summary = cursor.getString(cursor.getColumnIndexOrThrow(NewsDBHelper.SUMMARY))
//            val body = cursor.getString(cursor.getColumnIndexOrThrow(NewsDBHelper.BODY))
//            val imageUrl = cursor.getInt(cursor.getColumnIndexOrThrow(NewsDBHelper.IMAGE_URL))
//
//            newsList.add(NewsItem(id, title, summary, body, imageUrl))
//        }
//        cursor.close()
//        db.close()
//
//        // 创建 RecyclerViewAdapter 并将数据设置到 RecyclerView 中
//        val recyclerView2 = view.findViewById<RecyclerView>(R.id.recyclerView_text2)
//        val layoutManager2 = LinearLayoutManager(requireContext())
//        recyclerView2.layoutManager = layoutManager2
//        val adapter2 = NewsAdapter(newsList)
//        recyclerView2.adapter = adapter2
//    }

    // 处理从AddNewsActivity返回的数据
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
//            // 获取从 AddNewsActivity 传回来的数据
//            val title = data.getStringExtra("title")
//            val summary = data.getStringExtra("summary")
//            val newsPicture = data.getStringExtra("newsPicture")
//            val body = data.getStringExtra("body")
//
//            // 创建新的 general 对象，并设置图片URL
//            val newGeneral = news(
//                title = title ?: "",
//                summary =  summary ?: "",
//                pictureUrl = newsPicture ?: "",
//                body = body ?: "",
//            )
//
//            // 将新的 general 对象插入数据库，并更新 generallist
//            GlobalScope.launch(Dispatchers.IO) {
//                newsDao.insertGeneral(newGeneral)
//                withContext(Dispatchers.Main) {
//                    newsAdapter.insertGeneralAtTop(newGeneral)
//                }
//            }
//        }
//    }


    fun showToast(view: View){
        Toast.makeText ( requireContext(), "我被点击了", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // 移除 Handler 中所有未处理的消息，避免内存泄漏和闪退问题
        handler.removeCallbacksAndMessages(null)
    }

}