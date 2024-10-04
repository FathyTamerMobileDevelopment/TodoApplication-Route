package com.example.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.utils.formatDateOnly
import com.example.todoapp.utils.formatTimeOnly
import com.example.todoapp.utils.ignoreDate
import com.example.todoapp.utils.ignoreTime
import com.example.todoapp.utils.showDatePicker
import com.example.todoapp.utils.showTimePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskBottomSheet() : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTaskBinding

    val date = Calendar.getInstance().apply {
        ignoreTime()
    }
    val time = Calendar.getInstance().apply {
        ignoreDate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClicks()

    }

    private fun onClicks() {
        binding.selectTimeTv.setOnClickListener {
            requireContext().showTimePicker(time) { hour, minute ->
                binding.selectTimeTv.text = time.formatTimeOnly()
            }
        }
        binding.selectDateTv.setOnClickListener {
            requireContext().showDatePicker(date) { year, month, day ->
                binding.selectDateTv.text = date.formatDateOnly()
            }
        }
        binding.addTaskBtn.setOnClickListener {
            createTask()
        }
    }

    fun isValidForm(): Boolean {
        var isValid = true
        if (binding.title.text.toString().isBlank()) {
            isValid = false
            // show error
            binding.titleTil.error = "Please enter title"
        } else {
            binding.titleTil.error = null
        }

        if (binding.selectDateTv.text.toString().isBlank()) {
            isValid = false
            // show error
            binding.selectDateTil.error = "Please select date"
        } else {
            binding.selectDateTil.error = null
        }


        return isValid
    }

    private fun createTask() {
        if (!isValidForm()) return
        // create task
        AppDatabase.getInstance().tasksDao()
            .createTask(
                Task(
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    date = date.timeInMillis,
                    time = time.timeInMillis,
                )
            )

        findNavController().navigate(R.id.action_addTaskBottomSheet_to_taskFragment)

    }







}