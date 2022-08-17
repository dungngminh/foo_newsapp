package studio.komkat.android_newsapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.komkat.android_newsapp.databinding.ArticleCardBinding
import studio.komkat.android_newsapp.domain.model.Article

class HomeAdapter(
    private val onPressed: (article: Article) -> Unit,
) : ListAdapter<Article, HomeAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem
}) {

    inner class ViewHolder(private val binding: ArticleCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // handle click
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onPressed(getItem(position))
                }
            }
        }

        fun bind(article: Article) {
            binding.articleTitle.text = article.title ?: "No title";
            Picasso.get().load(article.urlToImage).into(binding.articleImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticleCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_card, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}