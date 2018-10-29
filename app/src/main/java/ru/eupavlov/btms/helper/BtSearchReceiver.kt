package ru.eupavlov.btms.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.bluetooth.BluetoothDevice
import android.util.Log
import ru.eupavlov.btms.model.BtDevice
import ru.eupavlov.btms.model.toBtDevice


class BtSearchReceiver : BroadcastReceiver() {
    val TAG = javaClass.simpleName
    var listener: (BtDevice) -> Unit = {}

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!=null) {
            if (BluetoothDevice.ACTION_FOUND == intent.action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                Log.e(TAG,"found new device: ${device.name}: ${device.address}")
                listener(device.toBtDevice(false))
            }
        }
    }

}