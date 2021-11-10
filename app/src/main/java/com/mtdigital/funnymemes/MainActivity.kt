package com.mtdigital.funnymemes

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    lateinit var img : ImageView
    lateinit var currentString : String
    lateinit var progress : ProgressBar
    lateinit var drawable: Drawable
    lateinit var bitMap : BitmapDrawable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress = findViewById(R.id.progressBar)
        loadMemes()
        img = findViewById<ImageView>(R.id.imageView)
    }

    fun loadMemes(){
    // Instantiate the RequestQueue.
        progress.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"


    // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currentString = response.getString("url")
                Glide.with(this).load(currentString).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        Toast.makeText(this@MainActivity,"Memes Load Failed",Toast.LENGTH_LONG).show()
                       return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                }).into(img)
            },
            {
                Log.e("Success","Your API is not working")
            })

    // Add the request to the RequestQueue.
        MemesSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun shareMemes(view: android.view.View) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT,"Hey Checkout this Memes from Reddit $currentString")
        val chooser = Intent.createChooser(i,"Share this memes using...")
        startActivity(chooser)
    }
    fun nextMemes(view: android.view.View) {
        loadMemes()
    }
}