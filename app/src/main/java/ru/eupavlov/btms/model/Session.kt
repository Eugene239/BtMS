package ru.eupavlov.btms.model

import android.bluetooth.BluetoothDevice
import android.util.Log
import org.koin.standalone.KoinComponent
import ru.eupavlov.btms.BT_TYPE
import java.io.File

class Session : KoinComponent {
    val TAG = javaClass.name

    var btType: BT_TYPE? = null
    var selectedBt: BluetoothDevice? = null
        set(btd) {
            btd?.let {
                Log.e(TAG, "session btd: $btd")
                btType = null
                field = btd
            }
        }

    var selectedFile: ByteArray? = null
        set(value) {
            Log.e(TAG, "session file: $value")
            btType = null
            field = value
        }

    fun isSelected(bt: BtDevice): Boolean {
        bt.device?.let {
            return it.address == selectedBt?.address
        }
        return false
    }

    override fun toString(): String {
        return "Session(selectedBt=$selectedBt)"
    }


}