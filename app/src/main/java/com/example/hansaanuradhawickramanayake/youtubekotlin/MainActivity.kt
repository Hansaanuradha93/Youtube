package com.example.hansaanuradhawickramanayake.youtubekotlin

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

// Json Url = https://www.youtube.com/redirect?q=https%3A%2F%2Fapi.letsbuildthatapp.com%2Fyoutube%2Fhome_feed&v=53BsyxwSBJk&redir_token=mhzzMeIHQUlHVfZS0aDLbpRSPv58MTUyNjMwODQyM0AxNTI2MjIyMDIz&event=video_description

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchJson()

    }

    private fun fetchJson() {
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback{
            override fun onResponse(call: Call?, response: Response?) {

                val body = response?.body()?.string()
                Log.i("json data", body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)


                runOnUiThread {
                    mainRecyclerView.adapter = MainAdapter(homeFeed)
                }


            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.i("failure", "Failed to fetch Json Data")
            }

        })
    }
}

