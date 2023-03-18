package com.example.myapplicationart.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myapplicationart.R
import com.example.myapplicationart.data.model.TAG

object DialogManager {

    interface Listener {
        fun onClick(name: String?)
    }

    fun showAlertDialog(context: Context, listenerEdit: Listener) {
        fun showToast(messageRes: String) =
            Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> showToast("Great!")
                DialogInterface.BUTTON_NEGATIVE -> showToast("Bay!")
                DialogInterface.BUTTON_NEUTRAL -> showToast("Don't worry")
            }
        }

        val edName = EditText(context)
        val dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle("Cite name:")
            .setView(edName)
            .setMessage("Enter city")
            .setNegativeButton("CANCEL", listener)
            .setNeutralButton("IGNORE", listener)
            .setOnCancelListener {
                showToast("You got out")
            }
            .setOnDismissListener {
                Log.d(TAG, "Dialog dismissed")
            }
            .create()

        dialog.window
            ?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.white)))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            listenerEdit.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(context, R.color.black))
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            .setTextColor(ContextCompat.getColor(context, R.color.black))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(context, R.color.black))
    }
}