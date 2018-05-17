package com.example.hansaanuradhawickramanayake.youtubekotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.course_lesson_row.view.*
import okhttp3.*
import java.io.IOException

class CourseDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set recycler view
        mainRecyclerView.layoutManager = LinearLayoutManager(this)

        // set nav bar title
        val navBarTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle

        // Fetch json data
        fetchJSON()
    }

    private fun fetchJSON(){

        // set url
        val videoId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)
        val courseDetailsUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=$videoId"

        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailsUrl).build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val courseLessons = gson.fromJson(body, Array<CourseLesson>::class.java)

                runOnUiThread {
                    mainRecyclerView.adapter = CourseDetailsAdapter(courseLessons)
                }
            }


        })
    }

    private class CourseDetailsAdapter(val courseLessons: Array<CourseLesson>): RecyclerView.Adapter<CourseDetailsViewHolder>() {
        override fun getItemCount(): Int {

            return courseLessons.size
        }

        override fun onBindViewHolder(holder: CourseDetailsViewHolder, position: Int) {

            val courseLesson = courseLessons.get(position)

            holder?.customView?.courseLessonTitleTextView?.text = courseLesson.name
            holder?.customView?.courseLessonDurationTextView?.text = courseLesson.duration


            val imageView = holder?.customView?.courseLessonThumbnailImageView
            Picasso.get().load(courseLesson.imageUrl).into(imageView)

            holder?.courseLesson = courseLesson

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDetailsViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val customView = layoutInflater.inflate(R.layout.course_lesson_row, parent, false)


            return CourseDetailsViewHolder(customView)
        }


    }

    class CourseDetailsViewHolder(val customView: View, var courseLesson: CourseLesson? = null): RecyclerView.ViewHolder(customView) {

        companion object {
            val COURSE_LESSON_LINK_KEY = "COURCE_LESSON_LINK"
        }

        init {
            customView.setOnClickListener {


                val intent = Intent(customView.context, CourseLessonActivity::class.java)

                intent.putExtra(COURSE_LESSON_LINK_KEY,courseLesson?.link)

                customView.context.startActivity(intent)
            }
        }

    }

}
