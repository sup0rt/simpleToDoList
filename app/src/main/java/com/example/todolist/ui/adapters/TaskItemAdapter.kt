package com.example.todolist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TaskItem
import com.example.todolist.interfaces.TaskItemListener
import com.example.todolist.databinding.TaskItemCellBinding
import com.example.todolist.ui.holders.TaskItemViewHolder

class TaskItemAdapter(
    private val taskItems: List<TaskItem>,
    private val clickListener: TaskItemListener
): RecyclerView.Adapter<TaskItemViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TaskItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskItemViewHolder(parent.context, binding, clickListener)
    }

    override fun onBindViewHolder(
        holder: TaskItemViewHolder,
        position: Int,
    ) {
        holder.bindindTaskItem(taskItems[position])
    }

    override fun getItemCount(): Int {
        return taskItems.size
    }

}