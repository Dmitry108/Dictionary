package ru.dim.dictionary.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main_recyclerview.view.*
import ru.dim.dictionary.R
import ru.dim.dictionary.model.entity.SearchResult

class HistoryRecyclerViewAdapter(private val listener: OnListItemClickListener) :
    RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder>() {

    private var data: List<SearchResult> = arrayListOf()

    fun setData(data: List<SearchResult>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history_recyclerview, parent, false))


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data = data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data: SearchResult){
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.headerTextView.text = data.text
                itemView.setOnClickListener {
                    listener.onItemClick(data)
                    Toast.makeText(itemView.context, "on click: ${data.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(searchResult: SearchResult)
    }
}