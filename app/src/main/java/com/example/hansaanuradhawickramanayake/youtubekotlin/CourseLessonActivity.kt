package com.example.hansaanuradhawickramanayake.youtubekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_course_lesson.*

class CourseLessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_lesson)

        val courseLessonLink = intent.getStringExtra(CourseDetailsActivity.CourseDetailsViewHolder.COURSE_LESSON_LINK_KEY)

        webViewCourseLesson.settings.javaScriptEnabled = true
        webViewCourseLesson.settings.loadWithOverviewMode = true
        webViewCourseLesson.settings.useWideViewPort = true
        webViewCourseLesson.loadUrl(courseLessonLink)
    }
}
