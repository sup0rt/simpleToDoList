package com.example.todolist

import com.example.todolist.model.TaskItem

interface TaskItemListener
{
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
}