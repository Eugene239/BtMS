package ru.eupavlov.btms.mvp.transfer

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.os.Environment.DIRECTORY_PICTURES
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.transfer_fragment.*
import ru.eupavlov.btms.BT_LIST_FRAGMENT
import ru.eupavlov.btms.BT_TYPE
import ru.eupavlov.btms.FILE_FRAGMENT
import ru.eupavlov.btms.R
import ru.eupavlov.btms.common.BaseFragment
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime


class TransferFragment : BaseFragment(), TransferView {


    @InjectPresenter
    lateinit var transferPresenter: TransferPresenter
    lateinit var me: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        me = BluetoothAdapter.getDefaultAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.transfer_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        masterCard.setOnClickListener {
            transferPresenter.setMaster()
            master.setImageDrawable(baseActivity.getDrawable(R.drawable.ic_baseline_mood_24px))
            slave.setImageDrawable(baseActivity.getDrawable(R.drawable.ic_twotone_mood_bad_24px))
        }
        slaveCard.setOnClickListener {
            transferPresenter.setSalve()
            master.setImageDrawable(baseActivity.getDrawable(R.drawable.ic_twotone_mood_24px))
            slave.setImageDrawable(baseActivity.getDrawable(R.drawable.ic_baseline_mood_bad_24px))
        }
        transferPresenter.getType()?.let {
            if (it == BT_TYPE.MASTER) {
                master.setImageDrawable(baseActivity.getDrawable(R.drawable.ic_baseline_mood_24px))
            } else {
                slave.setImageDrawable(baseActivity.getDrawable(R.drawable.ic_baseline_mood_bad_24px))
            }
        }
    }

    override fun startSlave() {

    }

    override fun showNoSelectedDevice() {
        Snackbar.make(view!!, R.string.select_device, 3000)
                .setAction(R.string.go_to) { baseActivity.changeFragment(BT_LIST_FRAGMENT) }
                .show()
    }

    override fun showNoSelectedFile() {
        Snackbar.make(view!!, R.string.select_file, 3000)
                .setAction(R.string.go_to) { baseActivity.changeFragment(FILE_FRAGMENT) }
                .show()
    }

    override fun updateProgress(float: Float) {
        baseActivity.runOnUiThread {
            progress_horizontal?.let {
                it.progress = float.toInt()
            }

        }
    }

    override fun saveFile(byteArray: ByteArray) {
        baseActivity.runOnUiThread {
            val file = File(baseActivity.getExternalFilesDir(DIRECTORY_PICTURES), "${System.currentTimeMillis()}.jpg")
            file.createNewFile()
            file.writeBytes(byteArray)
            Toast.makeText(baseActivity, "File saved: ${file.absolutePath}", LENGTH_LONG).show()
        }

    }

}