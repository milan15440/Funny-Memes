package com.mtdigital.funnymemes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val imageView: ImageView = findViewById(R.id.imageView3)
        Glide.with(this).load(R.drawable.funny_image_gif).into(imageView)

        Handler().postDelayed({
            val i = Intent(applicationContext,MainActivity::class.java)
            startActivity(i)
            finish()
        },3000)
    }
}