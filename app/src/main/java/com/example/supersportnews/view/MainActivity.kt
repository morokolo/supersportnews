package com.example.supersportnews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supersportnews.retrofit.ApiService
import com.example.supersportnews.R
import com.example.supersportnews.model.Article
import com.example.supersportnews.adapter.ArticleAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ipadfeed.supersport.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        val toast = Toast.makeText(applicationContext,"loading...", Toast.LENGTH_SHORT)
        toast.show()
        api.fetchAllUsers().enqueue(object : Callback<List<Article>> {

            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
               showData(response.body()!!)
                toast.cancel()
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                Toast.makeText(applicationContext,"Error loading news articles...Please try again",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showData(articles: List<Article>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager (this@MainActivity)
            adapter = ArticleAdapter(articles)
        }
    }

}
