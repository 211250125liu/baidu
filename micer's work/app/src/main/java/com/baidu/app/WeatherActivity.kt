package com.baidu.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Weather(val text:String, val imageId: Int)

class WeatherActivity : AppCompatActivity() {

    private val weatherDemo = ArrayList<Weather>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather)

        //recyclerview
        initText()
        val recyclerView = findViewById<RecyclerView>(R.id.weather_recycle)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        val adapter = weatherRecycler(weatherDemo)//上面
        recyclerView.adapter = adapter

    }

    private val LOADING_DELAY: Long = 300 // 300ms
    private lateinit var progressBar: ProgressBar

    // 点击跳转事件处理方法
    fun onImageViewClick(view: View) {
        // 显示 loading 界面
        progressBar = findViewById(R.id.progressBar1)
        progressBar.visibility = View.VISIBLE
//        val linearLayout1 =findViewById<LinearLayout>(R.id.ll5)
//        linearLayout1?.setBackgroundColor(Color.WHITE)

        // 延迟 300ms 后跳转到目标 Activity
        Handler(Looper.getMainLooper()).postDelayed({
            // 隐藏 loading 界面
            progressBar.visibility = View.GONE
            //linearLayout1?.setBackgroundColor(Color.TRANSPARENT)

            // 跳转到目标 Activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, LOADING_DELAY) // 延迟 300ms
    }

    private fun initText(){
        repeat(1){
            weatherDemo.add(Weather("现在",R.drawable.air))
            weatherDemo.add(Weather("1小时后",R.drawable.air))
            weatherDemo.add(Weather("2小时后",R.drawable.air))
            weatherDemo.add(Weather("3小时后",R.drawable.air))
            weatherDemo.add(Weather("4小时后",R.drawable.air))
            weatherDemo.add(Weather("5小时后",R.drawable.air))
            weatherDemo.add(Weather("6小时后",R.drawable.air))

        }


    }
}