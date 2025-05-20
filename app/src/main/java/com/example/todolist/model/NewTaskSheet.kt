package com.example.todolist.model

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.FragmentNewTaskSheetBinding
import com.example.todolist.views.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if(taskItem != null){
            binding.taskTitle.text = "Редактировать задачу"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.description.text = editable.newEditable(taskItem!!.desc)
            if(taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        }
        else{
            binding.taskTitle.text = "Новая задача"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener()
        {
            saveAction()
        }
        binding.selectTimeButton.setOnClickListener()
        {
            openTimePicker()
        }
        binding.deleteTaskButton.setOnClickListener()
        {
            deleteTask()
        }
    }

    private fun openTimePicker() {
        if(dueTime == null){
            dueTime = LocalTime.now()
        }
        val listener = TimePickerDialog.OnTimeSetListener{

            _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Вермя выполнения")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.selectTimeButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun NewTaskSheet.saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.description.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormatter.format(dueTime)

        if(taskItem == null){
            val newTask = TaskItem(name, desc, dueTimeString, null)
            taskViewModel.addTaskItem(newTask)
        }
        else{
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString
            taskViewModel.updateTaskItem(taskItem!!)
        }

        binding.name.setText("")
        binding.description.setText("")
        dismiss()
    }

    private fun NewTaskSheet.deleteTask(){
        if(taskItem != null){
            taskViewModel.deleteTaskItem(taskItem!!)
            binding.name.setText("")
            binding.description.setText("")
            dismiss()
        }
        else{
            dismiss()
        }
    }
}