package com.example.todolist

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapters.TaskItemAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.model.NewTaskSheet
import com.example.todolist.model.TaskItem
import com.example.todolist.views.TaskViewModel

class MainActivity : AppCompatActivity(), TaskItemListener
{
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as ToDoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTaskButton.setOnClickListener()
        {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val mainActivity = this

        taskViewModel.taskItems.observe(this)
        {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}