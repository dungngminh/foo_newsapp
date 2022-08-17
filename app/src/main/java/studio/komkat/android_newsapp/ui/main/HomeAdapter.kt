package studio.komkat.android_newsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.komkat.android_newsapp.R
import studio.komkat.android_newsapp.model.Article

class HomeAdapter(
    private val articles: Array<Article>,
    onPressed: (article: Article) -> Unit,
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleImage: ImageView
        val articleTitle: TextView

        init {
            articleImage = view.findViewById(R.id.articleImage)
            articleTitle = view.findViewById(R.id.articleTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_card, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.articleTitle.text = article.title ?: "No title";
        Picasso.get().load(article.urlToImage).into(holder.articleImage)
    }

    override fun getItemCount() = articles.size
}