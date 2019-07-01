package com.example.supersportnews.retrofit

import com.example.supersportnews.model.Article
import com.example.supersportnews.model.ArticleDetailsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/news?format=json")

    fun fetchAllUsers(): Call<List<Article>>

    @GET("/{SiteName}/{UrlName}/news/{UrlFriendlyDate}/{UrlFriendlyHeadline}?format=json")
    fun articleDetails(@Path("SiteName") SiteName: String, @Path("UrlName") UrlName: String, @Path("UrlFriendlyDate") UrlFriendlyDate: String, @Path("UrlFriendlyHeadline") UrlFriendlyHeadline: String): Call<ArticleDetailsModel>
}