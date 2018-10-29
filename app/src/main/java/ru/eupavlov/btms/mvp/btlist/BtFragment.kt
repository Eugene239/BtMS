package ru.eupavlov.btms.mvp.btlist

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.bt_device_item.view.*
import kotlinx.android.synthetic.main.bt_list_fragment.*
import ru.eupavlov.btms.R
import ru.eupavlov.btms.TRANSFER_FRAGMENT
import ru.eupavlov.btms.common.BaseFragment
import ru.eupavlov.btms.common.BaseListAdapter
import ru.eupavlov.btms.model.BtDevice
import ru.eupavlov.btms.showOrHide


class BtFragment : BaseFragment(), BtView {

    @InjectPresenter
    lateinit var presenter: BtPresenter
    private val btAdapter = BtAdapter()

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }
        baseActivity.registerReceiver(presenter.searchReceiver, filter)


        if (BluetoothAdapter.getDefaultAdapter().isDiscovering) {
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
        }
        Log.e(TAG, "startDiscovery")
        BluetoothAdapter.getDefaultAdapter().startDiscovery()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.bt_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.layoutManager = LinearLayoutManager(baseActivity, LinearLayout.VERTICAL, false)
        listView.adapter = btAdapter
        presenter.fillValues()

        refresh.setOnRefreshListener {
            update(emptyList())
            presenter.fillValues()
            if (!BluetoothAdapter.getDefaultAdapter().isDiscovering) BluetoothAdapter.getDefaultAdapter().startDiscovery()
            refresh.isRefreshing = false

        }

    }

    override fun update(devices: List<BtDevice>) {
        btAdapter.setValues(devices.sortedByDescending { it.paired })
    }

    override fun showError(e: Throwable) {
    }

    inner class ViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val deviceName = item.deviceName
        val paired = item.android_paired
        val selected = item.bt_connected
        val mac = item.macAddress
    }

    inner class BtAdapter : BaseListAdapter<BtDevice, ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.bt_device_item, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val device = list[position]
            val selected = presenter.isSelected(device)

            holder.deviceName.text = device.name
            holder.paired.showOrHide(device.paired)
            holder.selected.showOrHide(selected)
            holder.mac.showOrHide(device.device != null)
            holder.mac.text = device.device?.address
            holder.item.setOnClickListener {
                presenter.select(device)
                notifyDataSetChanged()
                baseActivity.changeFragment(TRANSFER_FRAGMENT)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        baseActivity.unregisterReceiver(presenter.searchReceiver)
        if (BluetoothAdapter.getDefaultAdapter().isDiscovering) {
            Log.e(TAG, "cancelDiscovery")
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
        }
    }

}