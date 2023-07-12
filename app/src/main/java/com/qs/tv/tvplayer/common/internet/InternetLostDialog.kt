package com.qs.tv.tvplayer.common.internet

import android.app.Dialog
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object InternetLostDialog {

    private var mDialog: Dialog? = null

    fun prepareDialog(context: Context) {

        val builder = MaterialAlertDialogBuilder(context)
        builder.setMessage("Please check your internet connection.")

        builder.setPositiveButton("Setting") { dialog, which ->

        }

        mDialog = builder.create()
        mDialog!!.setCancelable(false)
        mDialog!!.setCanceledOnTouchOutside(false)

    }

    fun show() {
        if (mDialog != null && !mDialog!!.isShowing) {
            mDialog?.show()
        }
    }

    fun hide() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog?.dismiss()
        }
    }

}