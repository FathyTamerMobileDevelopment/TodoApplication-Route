package com.example.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.FragmentEditTaskBinding
import com.example.todoapp.utils.formatDateOnly
import com.example.todoapp.utils.formatTimeOnly
import com.example.todoapp.utils.hideBottomAppBarViews
import com.example.todoapp.utils.ignoreDate
import com.example.todoapp.utils.ignoreTime
import com.example.todoapp.utils.showDatePicker
import com.example.todoapp.utils.showTimePicker
import java.util.Calendar


class EditTaskFragment : BaseFragment<FragmentEditTaskBinding>() {

    private val args by navArgs<EditTaskFragmentArgs>()

    val date = Calendar.getInstance().apply {
        ignoreTime()
    }
    val time = Calendar.getInstance().apply {
        ignoreDate()
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentEditTaskBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomAppBarViews()
        val task = args.task
        showTaskDetails(task)
        onClicks(task)
    }

    private fun onClicks(task: Task) {
        binding.btnSave.setOnClickListener {
            updateTask(task)
            findNavController().navigateUp()
        }
        binding.selectTimeTv.setOnClickListener {
            requireContext().showTimePicker(time) { hour, minute ->
                binding.selectTimeTv.text = time.formatTimeOnly()
                task.time = time.timeInMillis
            }
        }
        binding.selectDateTv.setOnClickListener {
            requireContext().showDatePicker(date) { year, month, day ->
                binding.selectDateTv.text = date.formatDateOnly()
                task.date = date.timeInMillis
            }
        }


    }

    private fun showTaskDetails(task: Task) {
        binding.title.setText(task.title)
        binding.description.setText(task.description)
        binding.selectTimeTv.text = task.time?.formatTimeOnly()
        binding.selectDateTv.text = task.date?.formatDateOnly()
    }

    private fun updateTask(task: Task) {
        val newTitle = binding.title.text.toString()
        val newDescription = binding.description.text.toString()

        task.title = newTitle
        task.description = newDescription



        AppDatabase.getInstance().tasksDao().updateTask(task)

    }


}