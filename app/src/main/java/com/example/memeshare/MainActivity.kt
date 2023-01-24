package com.example.memeshare

import android.app.Notification.Action
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    @Suppress("DEPRECATION")
    private fun loadMeme(){
       var currentImageUrl : String?= null
        progressBar.visibility=View.VISIBLE

        val url = " https://meme-api.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currentImageUrl = response.getString("url")

                Glide.with(this).load(currentImageUrl).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false

                    }

                }).into(imageView)
            },
            {  })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plane"
        var currentImageUrl = "https://meme-api.com/gimme "
        intent.putExtra(Intent.EXTRA_TEXT," hey check out this $currentImageUrl" )
        val chosoer=Intent.createChooser(intent,"share meme using this")
        startActivity(chosoer)



    }
}