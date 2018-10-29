package ru.eupavlov.btms.mvp.btlist

import android.bluetooth.BluetoothAdapter
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import org.koin.standalone.inject
import ru.eupavlov.btms.common.BasePresenter
import ru.eupavlov.btms.helper.BtSearchReceiver
import ru.eupavlov.btms.model.BtDevice
import ru.eupavlov.btms.model.Session
import ru.eupavlov.btms.model.toBtDevice

@InjectViewState
class BtPresenter : BasePresenter<BtView>() {
    private val session: Session by inject()
    private val adapter = BluetoothAdapter.getDefaultAdapter()
    private val hashMapDevices = HashMap<String, BtDevice>()
    val searchReceiver = BtSearchReceiver()

    init {
        searchReceiver.listener = {
            Log.e(TAG, "found new device: $it")
            hashMapDevices[it.device!!.address] = it
            viewState.update(hashMapDevices.values.toList())
        }
        fillBoundedDevices()
    }

    fun fillValues() {
        hashMapDevices.clear()
        fillBoundedDevices()
        viewState.update(hashMapDevices.values.toList())
    }

    fun isSelected(device: BtDevice): Boolean {
        return session.isSelected(device)
    }

    fun select(bt: BtDevice) {
        session.selectedBt = bt.device
    }


    private fun fillBoundedDevices() {
        adapter.bondedDevices.map { it.toBtDevice(true) }.forEach {
            hashMapDevices[it.device!!.address] = it
        }
    }

}