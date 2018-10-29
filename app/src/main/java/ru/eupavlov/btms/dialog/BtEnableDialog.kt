package ru.eupavlov.btms.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import ru.eupavlov.btms.R
import android.R.attr.tag
import android.util.Log


class BtEnableDialog : DialogFragment(){
    companion object {
        val TAG = javaClass.simpleName
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.enable_bt_notification)
        return builder.create()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        try {
            val ft = manager!!.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.e(TAG, "Exception", e)
        }

    }
    override fun showNow(manager: FragmentManager?, tag: String?) {
        try {
            val ft = manager!!.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.e(TAG, "Exception", e)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
    }

}