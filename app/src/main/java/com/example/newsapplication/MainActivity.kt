package com.example.newsapplication

import Article
import NewsResponse
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var newsList: List<Article>
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and Adapter
        newsRecyclerView = findViewById(R.id.newsRecyclerView)
        newsAdapter = NewsAdapter()
        newsRecyclerView.adapter = newsAdapter
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // Fetch News
        fetchNews()
    }

    private fun fetchNews() {
        val client = AsyncHttpClient()

        client["https://newsapi.org/v2/top-headlines?country=us&apiKey=e3d615b2ff7f4e2c97da21e135f9879c", object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                // Parse JSON response and update the adapter
                val newsJsonObj = json.jsonObject;
                Log.d("News data", json.toString())
                val newsResponse = NewsResponse(
                    newsJsonObj.getString("status"),
                    newsJsonObj.getInt("totalResults"),
                    Article.fromJsonArray(newsJsonObj.getJSONArray("articles"))
                )
                newsAdapter.setData(newsResponse.articles)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // called when response HTTP status is "4XX" (e.g., 401, 403, 404)
                Log.e("An error occurred", errorResponse)
            }
        }]
    }
}
