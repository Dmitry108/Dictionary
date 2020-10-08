package ru.dim.dictionary.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main_recyclerview.view.*
import ru.dim.dictionary.R
import ru.dim.dictionary.model.entity.SearchResult

class MainRecyclerViewAdapter (
    private var data: List<SearchResult>,
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<MainRecyclerViewAdapter.MainItemViewHolder>() {

    fun setData(data: List<SearchResult>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        return MainItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_main_recyclerview, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MainItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.headerTextView.text = data.text
                itemView.descriptionTextView.text = data.meanings[0].translation.text
                Picasso.with(itemView.context)
                    .load("https:${data.meanings[0].previewUrl}")
                    .into(itemView.imageView)
                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: SearchResult) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: SearchResult)
    }
}