package com.alif.practicefragmentandrecyclerview.chats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alif.practicefragmentandrecyclerview.R
import com.alif.practicefragmentandrecyclerview.chats.model.ChatModel

class ChatsAdapter(private val longClickListener: (ChatModel) -> Unit) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    private val items = ArrayList<ChatModel>()

    fun updateItems(items: List<ChatModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

   class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {


        private val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        private val lastMessageTextView = itemView.findViewById<TextView>(R.id.lastMessageTextView)

        fun bind(item: ChatModel) {
            titleTextView.text = item.title
            lastMessageTextView.text = item.lastMessage
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        val viewHolder = ViewHolder(itemView)
        // Устанавливаем обработчик долгого нажатия на элемент списка
        itemView.setOnLongClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                longClickListener.invoke(items[position])
            }
            true
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

}