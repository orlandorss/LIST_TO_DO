package com.example.list_to_do.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.list_to_do.R
import com.example.list_to_do.databinding.ItemTaskBinding
import com.example.list_to_do.model.Task

class TaskListAdapter: ListAdapter<Task,TaskListAdapter.TaskViewHolder>(DiffCallBack()) {
    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}
   inner class TaskViewHolder(private val binding:ItemTaskBinding
   ): RecyclerView.ViewHolder(binding.root){
        fun bind(item:Task){
            binding.tvTitle.text = item.title
            binding.tvDate.text = " ${item.date} ${item.hour}"
            binding.more.setOnClickListener{
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            val more = binding.more
           val PopupMenu = PopupMenu(more.context, more)
            PopupMenu.menuInflater.inflate(R.menu.popup_menu,PopupMenu.menu)
            PopupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.edit -> listenerEdit(item)
                    R.id.delet -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            PopupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val inflate = ItemTaskBinding.inflate(from, parent, false)
        return TaskViewHolder(inflate)

    }

    override fun onBindViewHolder(holder: TaskListAdapter.TaskViewHolder, position: Int) {
    holder.bind(getItem(position))
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id

}