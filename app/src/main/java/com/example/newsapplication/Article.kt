import org.json.JSONArray
import org.json.JSONObject

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) {
    companion object {
        fun fromJsonArray(jsonArray: JSONArray): List<Article> {
            val articles = mutableListOf<Article>()
            for (i in 0 until jsonArray.length()) {
                val articleJson = jsonArray.getJSONObject(i)
                val article = fromJson(articleJson)
                articles.add(article)
            }
            return articles
        }

        private fun fromJson(json: JSONObject): Article {
            val sourceJson = json.getJSONObject("source")
            val source = Source(sourceJson.getString("id"), sourceJson.getString("name"))

            return Article(
                source = source,
                author = json.optString("author", null),
                title = json.getString("title"),
                description = json.optString("description", null),
                url = json.getString("url"),
                urlToImage = json.optString("urlToImage", null),
                publishedAt = json.getString("publishedAt"),
                content = json.optString("content", null)
            )
        }
    }
}

data class Source(
    val id: String?,
    val name: String
)
