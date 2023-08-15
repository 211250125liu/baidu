package com.baidu.app

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.baidu.app.news.NewsDBHelper
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


//支持从相册选照片
class AddNewsActivity : AppCompatActivity() {
    lateinit var newsPicture : ImageView
    private var PictureUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_news)
        val backButton : ImageView = findViewById(R.id.backButton)

        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        newsPicture = findViewById(R.id.newsPicture2)

        newsPicture.setOnClickListener {
            showPictureDialog()
        }

        val commitButton : TextView = findViewById(R.id.commitNews)

        commitButton.setOnClickListener {
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val summary = findViewById<EditText>(R.id.summaryEditText).text.toString()
            val body = findViewById<EditText>(R.id.bodyEditText).text.toString()
            val newsPictureId = PictureUri

            // 访问数据库，先实例化 MyDBHelper 的子类
            val dbHelper = NewsDBHelper(this)
            // 以写入模式获取数据存储库
            val db: SQLiteDatabase = dbHelper.getWritableDatabase()
            val values = ContentValues()
            // 插入第一行数据
            values.put("title", title)
            values.put("pictureUrl", newsPictureId.toString())
            values.put("summary",summary)
            values.put("body",body)
            db.insert("News", null, values)
            // 插入其它数据前先清空
            values.clear()

//            // 创建Intent并添加数据
//            val resultIntent = Intent()
//            resultIntent.putExtra("title", title)
//            resultIntent.putExtra("summary", summary)
//            resultIntent.putExtra("newsPicture", PictureUri?.toString()) // 将选择图片的URI添加到Intent中
//            resultIntent.putExtra("body",body)
//
//            // 返回数据给上一个Activity（MainActivity）
//            setResult(Activity.RESULT_OK, resultIntent)
//            finish()


//            val newsDBHelper = NewsDBHelper(this)
//            val db = newsDBHelper.writableDatabase
//
//            val contentValues = ContentValues()
//            contentValues.put(NewsDBHelper.TITLE, title)
//            contentValues.put(NewsDBHelper.SUMMARY, summary)
//            contentValues.put(NewsDBHelper.BODY, body)
//            contentValues.put(NewsDBHelper.IMAGE_URL, newsPictureId)
//
//            val newRowId = db.insert(NewsDBHelper.TABLE_NAME, null, contentValues)
//
//            if(newRowId != -1L){
//                Log.i("insert sad","sad_sad")
//            }
//
//            db.close()

            Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }
    private fun showPictureDialog() {
        // 定义选择框的选项
        val options = arrayOf("从相册选择", "拍照(功能未完全实现)")
        // 创建选择框的 AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("选择图片来源")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    // 相册
                    openGallery()
                }
                1 -> {
                    // 拍照
                    openCamera()
                }
            }
            dialog.dismiss()
        }
        builder.show()
    }

    private var tempPhotoUri: Uri? = null


    // 打开相册的方法
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }
    //相机
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createPhotoFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.let {
                val photoUri = FileProvider.getUriForFile(
                    this,
                    "com.baidu.app.fileProvider",
                    it
                )
                // 保存临时文件的URI
                tempPhotoUri = photoUri
                // 将URI授权给相机应用
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, REQUEST_CODE_CAMERA)
            }
        } else {
            Toast.makeText(this, "无法打开相机", Toast.LENGTH_SHORT).show()
        }
    }

    // 处理相册或相机返回的结果
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    // 从相册选择返回的结果
                    PictureUri = data?.data
                    PictureUri?.let{
                        newsPicture.setImageURI(it)
                    }
                }
                REQUEST_CODE_CAMERA -> {
                    // 拍照返回的结果
                    if(tempPhotoUri != null){
                        newsPicture.setImageURI(tempPhotoUri)
                    }
                }
            }
        }
    }
    private var currentPhotoFile: File? = null
    @Throws(IOException::class)
    private fun createPhotoFile(): File {
        // 创建一个唯一的文件名，可以根据需要进行修改，这里用当前的时间戳作为文件名
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFileName = "IMG_${timeStamp}"
        val imageFile = File.createTempFile(
            imageFileName,  /* 文件名前缀 */
            ".jpg",  /* 文件扩展名 */
            storageDir /* 存储目录 */
        )
        // 保存当前拍照的临时文件
        currentPhotoFile = imageFile
        return imageFile
    }


    companion object {
        private const val REQUEST_CODE_GALLERY = 1
        private const val REQUEST_CODE_CAMERA = 2
    }
}