package com.example.supersportnews.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.supersportnews.retrofit.ApiService
import com.example.supersportnews.model.ArticleDetailsModel
import com.example.supersportnews.R
import com.example.supersportnews.adapter.ArticleAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ipadfeed.supersport.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)


        val siteName = intent.getStringExtra(ArticleAdapter.ViewHolder.SITE_NAME_KEY);
        val urlName = intent.getStringExtra(ArticleAdapter.ViewHolder.URL_NAME_KEY);
        val urlFriendlyDate = intent.getStringExtra(ArticleAdapter.ViewHolder.URL_FRIENDLY_DATE_KEY);
        val urlFriendlyHeadline = intent.getStringExtra(ArticleAdapter.ViewHolder.URL_FRIENDLY_HEADLINE_KEY);
        val toast = Toast.makeText(applicationContext,"loading...", Toast.LENGTH_SHORT)
        toast.show()


        api.articleDetails(siteName, urlName, urlFriendlyDate, urlFriendlyHeadline)
            .enqueue(object : Callback<ArticleDetailsModel> {
                override fun onResponse(call: Call<ArticleDetailsModel>, response: Response<ArticleDetailsModel>) {
                    toast.cancel()
                    displayArticleDetails(response.body()!!)
                }

                override fun onFailure(call: Call<ArticleDetailsModel>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error loading news article...Please try again",Toast.LENGTH_SHORT).show()
                }

            })
    }


    private fun displayArticleDetails(article: ArticleDetailsModel) {

        var thumbnailUrl = article.ImageUrlLocal + article.LargeImageName
        val thumnailImageView = findViewById(R.id.image) as ImageView


        Picasso.with(this).load(thumbnailUrl).into(thumnailImageView)

        val headline: TextView = findViewById(R.id.headline) as TextView
        headline.text = article.Headline

        val date: TextView = findViewById(R.id.date) as TextView
        date.text = article.UrlFriendlyDate


        val body: WebView = findViewById(R.id.body) as WebView
        body.loadData(article.Body, "text/html", "UTF-8")
    }
}
