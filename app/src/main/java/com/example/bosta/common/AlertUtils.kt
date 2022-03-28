package com.example.bosta.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bosta.R
import com.google.android.material.snackbar.Snackbar

fun displayErrorSnackbar(msg: String, activity: Activity) {
    val parentLayout = activity.findViewById<View>(android.R.id.content)
    val s = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT)
        .setTextColor(ContextCompat.getColor(activity, android.R.color.white))
    s.setActionTextColor(ContextCompat.getColor(activity, R.color.teal_200))
        .setAction(activity.getString(R.string.ok)) {
            s.dismiss()
        }
    s.show()
}

fun displayLongToast(
    context: Context,
    message: String?
) {
    Toast.makeText(context, message.toString(), Toast.LENGTH_LONG).show()
}

fun displayShortToast(
    context: Context,
    message: String?
) {
    Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
}
