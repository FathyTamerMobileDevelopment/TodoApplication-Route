package com.example.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.FragmentTaskBinding
import com.example.todoapp.ui.adapters.TaskListAdapter
import com.example.todoapp.utils.ignoreTime
import com.example.todoapp.utils.setDate
import com.example.todoapp.utils.showBottomAppBarViews
import com.example.todoapp.utils.showToolbarToDoTitle
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class TaskFragment : BaseFragment<FragmentTaskBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentTaskBinding.inflate(inflater, container, false)

    private val adapter = TaskListAdapter()
    private val selectedDate = Calendar.getInstance().apply { ignoreTime() }
    private var tasksList = mutableListOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapterWithClicks()
        setSelectedDate()

    }

    private fun setUpAdapterWithClicks() {
        binding.rvTasks.adapter = adapter
        adapter.onItemClick = TaskListAdapter.OnItemClickListener { task ->
            findNavController().navigate(
                TaskFragmentDirections.actionTaskFragmentToEditTaskFragment(
                    task
                )
            )
        }
        adapter.onDoneClick = TaskListAdapter.OnItemClickListener { task ->
            if (task.status) {
                task.status = false
                AppDatabase.getInstance().tasksDao().updateTask(task)
            } else {
                task.status = true
                AppDatabase.getInstance().tasksDao().updateTask(task)
            }
            getTasksFromDatabase()
        }

        adapter.onDeleteClick = TaskListAdapter.OnItemClickListener { task ->
            AppDatabase.getInstance()
                .tasksDao()
                .deleteTask(task)

            getTasksFromDatabase()
        }

    }

    private fun setSelectedDate() {
        binding.calendarView.setDateSelected(
            CalendarDay.today(), true
        )
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedDate.setDate(
                date.year,
                date.month - 1,
                date.day
            )
            getTasksFromDatabase()
        }
    }

    override fun onResume() {
        super.onResume()
        getTasksFromDatabase()
        showBottomAppBarViews()
        showToolbarToDoTitle()
    }

    fun getTasksFromDatabase() {
        tasksList = AppDatabase
            .getInstance()
            .tasksDao()
            .getTasksByDate(selectedDate.timeInMillis)

        adapter.submitNewList(tasksList)

    }


}



















