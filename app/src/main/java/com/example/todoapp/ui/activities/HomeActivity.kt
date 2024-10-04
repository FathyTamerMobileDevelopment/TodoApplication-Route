package com.example.todoapp.ui.activities

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp.R
import com.example.todoapp.base.BaseActivity
import com.example.todoapp.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun inflateBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linkNavHostWithBottomNavigation()

        onClicks()

    }

    private fun onClicks() {
        binding.fabAddTask.setOnClickListener {
            showAddTaskBottomSheet()
        }
    }

    private fun showAddTaskBottomSheet() {
        findNavController(R.id.fragmentContainerView).navigate(R.id.addTaskBottomSheet)
    }

    private fun linkNavHostWithBottomNavigation() {
        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
    }


}