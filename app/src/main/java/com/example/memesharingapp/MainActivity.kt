package com.example.memesharingapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity() {
var curretnImageUel:String? = null
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        loadMeme()

// ...
button2.setOnClickListener {
    loadMeme()
}
// Instantiate the RequestQueue.
button.setOnClickListener {
    sharememe()
}
    }

    private fun sharememe() {
val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "hey i found this amazing meme on reddit $curretnImageUel")
    val chooser = Intent.createChooser(intent,"share this message to....")
        startActivity(chooser)
    }

    private fun loadMeme() {
        textView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        val queue = MySingleton.getInstance(this.applicationContext).requestQueue

        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val request = JsonObjectRequest(
            Request.Method.GET, url,null,

            { response ->
                curretnImageUel = response.getString("url")
                Glide.with(this).load(curretnImageUel).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e : GlideException? ,
                        model : Any? ,
                        target : Target<Drawable>? ,
                        isFirstResource : Boolean
                    ) : Boolean {
progressBar.visibility = View.GONE
                textView.visibility = View.VISIBLE
                    return false
                    }

                    override fun onResourceReady(
                        resource : Drawable? ,
                        model : Any? ,
                        target : Target<Drawable>? ,
                        dataSource : DataSource? ,
                        isFirstResource : Boolean
                    ) : Boolean {
                        progressBar.visibility = View.GONE
                        textView.visibility = View.VISIBLE
                        return false
                    }
                }).into(imageView)
                // Display the first 500 characters of the response string.
                val name = response.getString("title")
                textView.text = name
            } ,
            {
                Toast.makeText(this , "an error Occurred" , Toast.LENGTH_SHORT).show()
            })
   queue.add(request)
  }
// Add the request to the RequestQueue.

    }
