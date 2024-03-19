package com.alif.practicefragmentandrecyclerview.chats

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alif.practicefragmentandrecyclerview.R
import com.alif.practicefragmentandrecyclerview.chats.adapter.ChatsAdapter
import com.alif.practicefragmentandrecyclerview.chats.model.ChatModel

class ChatFragment : Fragment(R.layout.fragment_chats) {

    private val recyclerView by lazy { requireView().findViewById<RecyclerView>(R.id.recyclerView) }

    private val adapter = ChatsAdapter { chatModel ->
        showDeleteConfirmationDialog(chatModel)
    }


    private val data =mutableListOf (
        ChatModel("Bryan", "What do you think?"),
        ChatModel("Kari", "Looks great!"),
        ChatModel("Diana", "Lunch on Monday?"),
        ChatModel("Ben", "You sent a photo."),
        ChatModel("Naomi", "Naomi sent a photo."),
        ChatModel("Alicia", "See you at 8."),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        adapter.updateItems(data)
        view.findViewById<EditText>(R.id.searchEditText).apply {
            background = null
            doAfterTextChanged {
                filterChats(it)
            }
        }
    }

    private fun filterChats(query: Editable?) {
        query?.let {
            if (it.isNotEmpty()) {
                val searchQuery = query.toString().lowercase()
                adapter.updateItems(data.filter {
                    it.title.lowercase().contains(searchQuery) ||
                            it.lastMessage.lowercase().contains(searchQuery)
                })
            } else adapter.updateItems(data)
        }
    }

    private fun showDeleteConfirmationDialog(chatModel: ChatModel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Chat")
            .setMessage("Are you sure you want to delete this chat?")
            .setPositiveButton("Delete") { dialogInterface: DialogInterface, _: Int ->
                // Удаляем чат из списка
                data.remove(chatModel)
                // Обновляем список в адаптере
                updateChatList(data)
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss() // Отмена удаления
            }
            .show()
    }

    private fun updateChatList(newData: List<ChatModel>) {
        adapter.updateItems(newData)
    }

}