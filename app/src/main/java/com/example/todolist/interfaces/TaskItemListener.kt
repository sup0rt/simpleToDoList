package com.example.todolist.interfaces

import com.example.todolist.data.TaskItem

interface TaskItemListener
{
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
}