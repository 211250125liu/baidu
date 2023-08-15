package com.baidu.app


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val home = findViewById<LinearLayout>(R.id.home)
        val homeImage = findViewById<ImageView>(R.id.bottomImage1)
        val homeText = findViewById<TextView>(R.id.bottomText1)
        val video = findViewById<LinearLayout>(R.id.video)
        val videoImage = findViewById<ImageView>(R.id.bottomImage2)
        val videoText = findViewById<TextView>(R.id.bottomText2)
        val user = findViewById<LinearLayout>(R.id.user)
        val userImage = findViewById<ImageView>(R.id.bottomImage3)
        val userText = findViewById<TextView>(R.id.bottomText3)

        //默认的home
        showFragment(HomeFragment())
        setSelectedNavItem(homeText, homeImage)

        //导航栏切换
        home.setOnClickListener {
            showFragment(HomeFragment())
            setSelectedNavItem(homeText, homeImage)
            clearSelectedNavItem(videoText, videoImage)
            clearSelectedNavItem(userText, userImage)
        }
        video.setOnClickListener {
            showFragment(VideoFragment())
            setSelectedNavItem(videoText, videoImage)
            clearSelectedNavItem(homeText, homeImage)
            clearSelectedNavItem(userText, userImage)
        }
        user.setOnClickListener {
            showFragment(UserFragment())
            setSelectedNavItem(userText, userImage)
            clearSelectedNavItem(homeText, homeImage)
            clearSelectedNavItem(videoText, videoImage)
        }

    }

    private val textList = ArrayList<Text>()
    private val textImageList = ArrayList<TextImage>()

    /**
     * 添加RecycleView
     */
//    private fun addRecycleView() {
//        // 从布局中进行获取
////         recycleView = findViewById(R.id.rv_demo)
//        initText1()
//        val recycler1 = findViewById<RecyclerView>(R.id.recyclerView_text1)
//        val layoutManager1 = LinearLayoutManager(this)
//        recycler1.layoutManager = layoutManager1
//        val adapter1 = recyclerViewAdapter_1(textList)
//        recycler1.adapter = adapter1
//
//        initText2()
//        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerView_text2)
//        val layoutManager2 = LinearLayoutManager(this)
//        recyclerView2.layoutManager = layoutManager2
//        val adapter2 = recyclerViewAdapter_2(textImageList)
//        System.out.println(adapter2)
//        recyclerView2.adapter = adapter2
//    }


//    private fun initText1(){
//        repeat(1){
//            textList.add(Text("这些重大问题，习近平的回答掷地有声","置顶","新华社新媒体"))
//            textList.add(Text("习近平会见”元老会“代表团","置顶", "央视网新闻"))
//        }
//
//
//    }
//    private fun initText2(){
//        repeat(4){
//            textImageList.add(TextImage("美媒：五角大楼盯上谷歌在华AI中心 谷歌忙安抚","热点","热点消息",R.drawable.anim_1))
//            textImageList.add(TextImage("蔡英文财产曝光：存款5406万 名下拥有6笔不动产","热点","热点消息", R.drawable.anim_2))
//        }
//    }

    private val LOADING_DELAY: Long = 1000 // 150ms
    private lateinit var progressBar: ProgressBar

    /**一个fragment调到另一个fragment
//    getActivity().getSupportFragmentManager()
//    .beginTransaction()
//    .replace(R.id.xx, new XxxFragment(), null)
//    .addToBackStack(null)
//    .commit();
    */
    /**fragment调到另一个activity
     * Intent intent = new Intent(getActivity(),OtherActivity.class);
    startActivity(intent);
     */


//    fun ClickVideo(view:View){
//        progressBar = findViewById(R.id.progressBar1)
//        progressBar.visibility = View.VISIBLE
//        val linearLayout1 =findViewById<LinearLayout>(R.id.video)
//        linearLayout1?.setBackgroundColor(Color.WHITE)
//
//        // 延迟 150ms 后跳转到目标 Activity
//        Handler(Looper.getMainLooper()).postDelayed({
//            // 隐藏 loading 界面
//            progressBar.visibility = View.GONE
//
//            // 跳转到目标 Activity
//            val intent = Intent(this, weatherActivity::class.java)
//            startActivity(intent)
//
//        }, LOADING_DELAY) // 延迟 300ms
//    }
//    fun ClickUser(view:View){
//        progressBar = findViewById(R.id.progressBar1)
//        progressBar.visibility = View.VISIBLE
//        val linearLayout1 =findViewById<LinearLayout>(R.id.video)
//        linearLayout1?.setBackgroundColor(Color.WHITE)
//
//        // 延迟 150ms 后跳转到目标 Activity
//        Handler(Looper.getMainLooper()).postDelayed({
//            // 隐藏 loading 界面
//            progressBar.visibility = View.GONE
//
//            // 跳转到目标 Activity
//            val intent = Intent(this, userFragment::class.java)
//            startActivity(intent)
//
//        }, LOADING_DELAY) // 延迟 300ms
//    }

    // 点击事件处理方法


//    fun showToast(view: View){
//        Toast.makeText ( this, "我被点击了", Toast.LENGTH_SHORT).show()
//    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }

    private fun setSelectedNavItem(textView: TextView, imageView: ImageView) {
        textView.isSelected = true
        imageView.isSelected = true
    }

    private fun clearSelectedNavItem(textView: TextView, imageView: ImageView) {
        textView.isSelected = false
        imageView.isSelected = false
    }


}

