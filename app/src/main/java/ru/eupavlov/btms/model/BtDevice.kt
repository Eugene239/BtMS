package ru.eupavlov.btms.model

import android.bluetooth.BluetoothDevice

data class BtDevice(val device: BluetoothDevice? = null,
                    val name: String? = "NO_NAME",
                    val paired: Boolean = false)

fun BluetoothDevice.toBtDevice(paired: Boolean = false): BtDevice {
    return BtDevice(this, this.name, paired)
}