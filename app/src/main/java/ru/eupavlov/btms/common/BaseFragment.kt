package ru.eupavlov.btms.common

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatFragment

open class BaseFragment : MvpAppCompatFragment() {
    val TAG = javaClass.name
  //  private var progressDialog: ProgressBar? = null
    lateinit var baseActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.e(TAG, "ATTACH")
        if (context is BaseActivity) {
            baseActivity = context
         //   progressDialog = baseActivity.findViewById(R.id.progress)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "ON_RESUME")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "DETACH")
    }

//    fun showError(e: Throwable) {
//        if (e is NumberFormatException) {
//
//        } else {
//            Snackbar.make(this.view!!, R.string.unhandled_error, Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
//    }


//   open fun showLoading(show: Boolean) {
//        if (show) {
//            if (waitDialog == null && !baseActivity.isFinishing) {
//                Log.e(TAG, "startUpdate: launch wait Dialog")
//                waitDialog = WaitDialog(baseActivity)
//                waitDialog?.show()
//            }
//        } else {
//            waitDialog?.dismiss()
//            waitDialog = null
//        }
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        waitDialog?.dismiss()
//    }


}