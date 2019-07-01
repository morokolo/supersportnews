package com.example.supersportnews.adapter

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.supersportnews.R
import com.example.supersportnews.model.Article
import com.example.supersportnews.view.NewsDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_row.view.*

class ArticleAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
      return ViewHolder(view)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = articles[position]
        holder.category.text = user.Headline
        holder.headline.text = user.Category
        //holder.articleDate.text = article.Date
        holder.articleBlurb.text = user.Blurb
        var thumbnailUrl = user.ImageUrlRemote + user.SmallImageName
        val thumnailImageView = holder.itemView.photo
        Picasso.with(holder.itemView.context).load(thumbnailUrl).into(thumnailImageView);

        holder?.article = user
    }

    class ViewHolder(itemView: View, var article : Article? = null ) : RecyclerView.ViewHolder(itemView) {

        companion object {
            val SITE_NAME_KEY = "siteName"
            val URL_NAME_KEY = "urlName"
            val URL_FRIENDLY_DATE_KEY = "urlFriendlyDate"
            val URL_FRIENDLY_HEADLINE_KEY = "urlFriendlyHeadline"
        }

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDetail::class.java)
                intent.putExtra(SITE_NAME_KEY, article?.SiteName)
                intent.putExtra(URL_NAME_KEY, article?.UrlName)
                intent.putExtra(URL_FRIENDLY_DATE_KEY, article?.UrlFriendlyDate)
                intent.putExtra(URL_FRIENDLY_HEADLINE_KEY, article?.UrlFriendlyHeadline)

                itemView.context.startActivity(intent);
            }
        }
        val category: TextView = itemView.category
        val headline: TextView = itemView.headline
        //val articleDate: TextView = itemView.articleDate
        val articleBlurb: TextView = itemView.articleBlurb

    }
}
