package ru.eupavlov.btms.helper

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BtStateChanges : BroadcastReceiver() {
    val TAG = javaClass.simpleName
    var listener: (Int) -> Unit? = {}

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if (intent.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                listener(state)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        Log.e(TAG, "BluetoothAdapter.STATE_OFF")
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        Log.e(TAG, "BluetoothAdapter.STATE_TURNING_OFF")
                    }
                    BluetoothAdapter.STATE_ON -> {
                        Log.e(TAG, "BluetoothAdapter.STATE_ON")
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        Log.e(TAG, "BluetoothAdapter.STATE_TURNING_ON")
                    }
                }
            }
        }
    }

}

