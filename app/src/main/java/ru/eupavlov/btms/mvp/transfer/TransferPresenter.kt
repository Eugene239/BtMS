package ru.eupavlov.btms.mvp.transfer

import android.bluetooth.BluetoothAdapter
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import org.koin.standalone.inject
import ru.eupavlov.btms.APP_UUID
import ru.eupavlov.btms.BT_TYPE
import ru.eupavlov.btms.common.BasePresenter
import ru.eupavlov.btms.model.Session

@InjectViewState
class TransferPresenter : BasePresenter<TransferView>() {
    private val session: Session by inject()

    private var slaveThread: ConnectSlaveThread? = null
    private var masterThread: ConnectMasterThread? = null

    fun getType(): BT_TYPE? {
        return session.btType
    }

    fun setSalve() {
        session.btType = BT_TYPE.SLAVE
        if (session.selectedBt != null) {
            if (masterThread != null) {
                masterThread!!.interrupt()
                masterThread = null
            }
            val device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(session.selectedBt!!.address)
            slaveThread = ConnectSlaveThread(device).apply {
                this.listener = { progress ->
                    viewState.updateProgress(progress)
                }
                this.fileListener = {bytes ->
                    viewState.saveFile(bytes)
                }
            }
            slaveThread!!.start()
        } else {
            viewState.showNoSelectedDevice()
        }
    }

    fun setMaster() {
        session.btType = BT_TYPE.MASTER
        if (session.selectedFile != null) {
            if (slaveThread != null) {
                slaveThread!!.interrupt()
                slaveThread = null
            }
            masterThread = ConnectMasterThread(session.selectedFile!!).apply {
                this.listener = { progress ->
                    viewState.updateProgress(progress)
                }
            }
            masterThread!!.start()
        } else {
            viewState.showNoSelectedFile()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        masterThread?.interrupt()
        slaveThread?.interrupt()
    }


}