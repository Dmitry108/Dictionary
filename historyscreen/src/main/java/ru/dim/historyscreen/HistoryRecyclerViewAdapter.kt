package ru.dim.historyscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_recyclerview.view.*
import ru.dim.model.entity.SearchResult

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
        private val headerTextView by lazy<TextView>{ itemView.findViewById(R.id.header_textView) }
        fun bind(data: SearchResult){
            if (layoutPosition != RecyclerView.NO_POSITION) {
                headerTextView.text = data.text
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