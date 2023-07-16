package com.example.newsapp


import android.app.Activity
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class Adapter(private val activity: Activity, private val newsList: List<Articles>) : RecyclerView.Adapter<Adapter.viewHolder>() {

    class viewHolder(view: View):RecyclerView.ViewHolder(view)  {
        val img: ImageView = view.findViewById(R.id.newsImg)
        val title: TextView = view.findViewById(R.id.title)
        val time: TextView = view.findViewById(R.id.date)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val cardView : CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rv_news, parent, false)

        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        holder.title.text = newsList[position].title

        holder.time.text = newsList[position].publishedAt

        //load img to imageView
        Glide.with(activity).load(newsList[position].urlToImage)
            .listener(object :RequestListener<Drawable>{

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean,
            ): Boolean {
                holder.img.setImageResource(R.drawable.loading_failed)
                holder.progressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean,
            ): Boolean {
                holder.progressBar.visibility = View.GONE
                return false
            }

        }).into(holder.img)

        holder.cardView.setOnClickListener{
           openNewsOnCustomTab(position)
        }

    }

    private fun openNewsOnCustomTab(position: Int) {

        //it will not open in chrome
        val url = newsList[position].url
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(activity, R.color.purple_200))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(activity, Uri.parse(url))

    }

}
