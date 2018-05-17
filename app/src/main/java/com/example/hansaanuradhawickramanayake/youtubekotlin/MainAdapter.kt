package com.example.hansaanuradhawickramanayake.youtubekotlin

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {

    val videoTitles = listOf<String>("First", "Second", "3rd", "4th", "5th")

    // No of rows
    override fun getItemCount(): Int {

        return homeFeed.videos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

//        val videoTitle = videoTitles.get(position)

        val video = homeFeed.videos.get(position)

        holder?.view?.videoTitleTextView?.text = video.name

        holder?.view?.channelNameTextView?.text = video.channel.name + "    -   " + "20K Views\n" +
                "4days ago"

        val videoThumbnailImageView = holder?.view?.videoTumbnailImageView
        Picasso.get().load(video.imageUrl).into(videoThumbnailImageView)

        val channelProfileImageView = holder?.view?.channelProfileImageView
        Picasso.get().load(video.channel.profileImageUrl).into(channelProfileImageView)

        holder?.video = video



    }



}

class CustomViewHolder(val view: View, var video: Video ?= null): RecyclerView.ViewHolder(view){


    companion object {
            val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }

    init {
        view.setOnClickListener {

            val intent = Intent(view.context, CourseDetailsActivity::class.java)

            intent.putExtra(VIDEO_TITLE_KEY, video?.name)

            intent.putExtra(VIDEO_ID_KEY, video?.id)
            view.context.startActivity(intent)
        }
    }

}


