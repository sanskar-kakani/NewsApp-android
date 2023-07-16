package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView:RecyclerView
    lateinit var list :List<Articles>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // off dark mode
        setContentView(R.layout.activity_main)

        val businessBtn = findViewById<Button>(R.id.business)
        val entertainmentBtn = findViewById<Button>(R.id.entertainment)
        val generalBtn = findViewById<Button>(R.id.general)
        val healthBtn = findViewById<Button>(R.id.health)
        val scienceBtn = findViewById<Button>(R.id.science)
        val sportsBtn = findViewById<Button>(R.id.sports)
        val technologyBtn = findViewById<Button>(R.id.technology)

        getNews("") // by default general

        businessBtn.setOnClickListener {
            getNews("business")
        }
        entertainmentBtn.setOnClickListener {
            getNews("entertainment")
        }
        generalBtn.setOnClickListener {
            getNews("general")
        }
        healthBtn.setOnClickListener {
            getNews("health")
        }
        scienceBtn.setOnClickListener {
            getNews("science")
        }
        sportsBtn.setOnClickListener {
            getNews("sports")
        }
        technologyBtn.setOnClickListener {
            getNews("technology")
        }

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun getNews(category: String) {

        val news = NewsService.newsInterface.getNews("in" , category)

        news.enqueue(object :Callback<News> {

            override fun onResponse(p0: Call<News>, response: Response<News>) {
                val news = response.body()

                if (news != null ){
                    list  = news.articles
                    val adapter = Adapter(this@MainActivity, news.articles)
                    recyclerView.adapter = adapter

                    adapter.notifyDataSetChanged() // change rv on button click

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("sanskar" , t.message.toString())
            }

        })
    }
}