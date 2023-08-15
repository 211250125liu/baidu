package com.baidu.app

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.baidu.app.user.UserDBHelper

class UserFragment : Fragment() {

    private val REQUEST_CODE_GALLERY_FOR_AVATAR = 1002
    private lateinit var avatarImageView : ImageView
    private lateinit var userNameTextView: TextView
    private lateinit var userAvatar: ImageView
    private var selectedAvatarResId: Int = R.drawable.anim_1
    private var userName: String = "micer"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user, container, false)
        val changeUser = view.findViewById<TextView>(R.id.change_user)
        userNameTextView = view.findViewById(R.id.usernameTextView)
        userAvatar = view.findViewById(R.id.avatarImageView)

        // 设置用户信息显示
        userNameTextView.text = userName
        userAvatar.setImageResource(selectedAvatarResId)

        changeUser.setOnClickListener {
            showCustomDialog()
        }

        return view
    }

    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val userNameEditText = dialogView.findViewById<EditText>(R.id.userNameEditText)
        avatarImageView = dialogView.findViewById<ImageView>(R.id.avatarImageView)
        val change_user_ok = dialogView.findViewById<TextView>(R.id.change_user_ok)
        // 创建自定义对话框
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        change_user_ok.setOnClickListener{
            val newUserName = userNameEditText.text.toString()
            userName = newUserName
            userNameTextView.text = userName
            dialog.dismiss()
        }

        avatarImageView.setOnClickListener {
            openGalleryForAvatar()
        }

        // 访问数据库，先实例化 MyDBHelper 的子类
        val dbHelper = UserDBHelper(requireContext())
        // 以写入模式获取数据存储库
        val db: SQLiteDatabase = dbHelper.getWritableDatabase()
        val values = ContentValues()
        // 插入第一行数据
        values.put("name", userName)
        values.put("pictureUrl", userAvatar.toString())
        // 插入其它数据前先清空
        values.clear()

        // 显示对话框
        dialog.show()
    }

    private fun openGalleryForAvatar() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_GALLERY_FOR_AVATAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_GALLERY_FOR_AVATAR) {
            // 从相册选择返回的结果
            val selectedImageUri = data?.data
            selectedImageUri?.let {
                // 更新 userName 和 userAvatar 的显示
//                userNameTextView.text = userName
//                userAvatar.setImageResource(selectedAvatarResId)
                userAvatar.setImageURI(it)
                avatarImageView.setImageURI(it)
            }
        }
    }
}
