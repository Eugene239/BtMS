package ru.eupavlov.btms

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import ru.eupavlov.btms.common.BaseActivity
import ru.eupavlov.btms.common.BaseFragment
import ru.eupavlov.btms.dialog.BtEnableDialog
import ru.eupavlov.btms.helper.BtStateChanges


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private val btBroadcastReceiver = BtStateChanges()
    private val btEnableDialog = BtEnableDialog()

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_btlist -> {
                changeFragment(BT_LIST_FRAGMENT)
                return true
            }
            R.id.navigation_file -> {
                changeFragment(FILE_FRAGMENT)
                return true
            }
            R.id.navigation_transfer -> {
                changeFragment(TRANSFER_FRAGMENT)
                return true
            }
        }
        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == -1)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), ENABLE_SCAN)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == -1)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), ENABLE_WRITE)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == -1)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), ENABLE_READ)

        registerReceiver(
                btBroadcastReceiver.apply {
                    listener = { state ->
                        when (state) {
                            BluetoothAdapter.STATE_TURNING_ON -> {
                                if (btEnableDialog.isAdded) btEnableDialog.dismissAllowingStateLoss()
                            }
                            BluetoothAdapter.STATE_ON->{
                                if(selectedFragment==null){
                                    changeFragment(BT_LIST_FRAGMENT)
                                }
                            }
                            BluetoothAdapter.STATE_OFF -> {
                                changeFragment(BaseFragment())
                                enableBt()
                            }

                        }
                    }
                },
                IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        )

        enableBt()

        navigation.setOnNavigationItemSelectedListener(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ENABLE_BT -> if (requestCode != -1) {
                enableBt()
            }
        }
        Log.e(TAG, "activityResult: $requestCode, $resultCode $data")
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(btBroadcastReceiver)
    }

    private fun enableBt() {

        val bt = BluetoothAdapter.getDefaultAdapter()
        bt?.let {
            if (!bt.isEnabled) {
                if (!btEnableDialog.isAdded) btEnableDialog.show(supportFragmentManager, btEnableDialog.tag)
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, ENABLE_BT)
            }else{
                changeFragment(BT_LIST_FRAGMENT)
            }
        }
    }

    override fun changeFragment(fragmentId: Int) {
        super.changeFragment(fragmentId)

    }


}
