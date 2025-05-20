package com.example.todolist.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.TaskItemRepository
import com.example.todolist.model.TaskItem
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskItemRepository) : ViewModel()
{
    var taskItems: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()


    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(newTask)
    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)
    }

    fun deleteTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.deleteTaskItem(taskItem)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if(!taskItem.isCompleted())
            taskItem.completedDateString = TaskItem.dateFormatter.format(LocalDate.now())
        repository.updateTaskItem(taskItem)
    }
}