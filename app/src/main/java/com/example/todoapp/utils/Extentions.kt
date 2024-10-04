package com.example.todoapp.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun Fragment.hideBottomAppBarViews() {
    requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_Task).visibility = View.GONE
    requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar).visibility = View.GONE

    // set marginBottom = 0
    val constraintLayout = requireActivity().findViewById<ConstraintLayout>(R.id.constraint)
    val params = constraintLayout.layoutParams as ViewGroup.MarginLayoutParams
    params.bottomMargin = 0
    constraintLayout.layoutParams = params
}

fun Fragment.showBottomAppBarViews() {
    requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_Task).visibility =
        View.VISIBLE
    requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar).visibility = View.VISIBLE

    // set marginBottom = actionBarSize
    val typedValue = TypedValue()
    val theme = requireActivity().theme
    theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
    val actionBarSize =
        TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)

    val constraintLayout = requireActivity().findViewById<ConstraintLayout>(R.id.constraint)
    val params = constraintLayout.layoutParams as ViewGroup.MarginLayoutParams
    params.bottomMargin = actionBarSize
    constraintLayout.layoutParams = params

}


fun Context.showSuccessDialog(fragment: Fragment? = null) {
    val dialog = AlertDialog.Builder(this)
        .setMessage(getString(R.string.task_added_successfully))
        .setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
            fragment?.let {
                if (it is DialogFragment) {
                    it.dismiss()
                }
            }
        }
        .setCancelable(false)
    dialog.show()
}

fun Fragment.showToolbarSettingTitle(){
    activity?.findViewById<TextView>(R.id.tv_toolbar_title)?.setText(R.string.settings)
}
fun Fragment.showToolbarToDoTitle(){
    activity?.findViewById<TextView>(R.id.tv_toolbar_title)?.setText(R.string.to_do_list)
}