package com.ded.goalmatch.mainfragment

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {
    fun showDialog(
        context: Context,
        title: String,
        message: String? = null,
        yesBtn: String,
        noBtn: String? = null,
        yesClickListener: DialogInterface.OnClickListener? = null,
        noClickListener: DialogInterface.OnClickListener? = null
    ) {
        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(yesBtn, yesClickListener)



        if (noBtn != null)
            dialog.setNegativeButton(noBtn, noClickListener)
        dialog.setCancelable(false)
            .create()
            .show()

    }
}