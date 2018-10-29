package ru.eupavlov.btms.mvp.transfer

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import ru.eupavlov.btms.APP_UUID
import ru.eupavlov.btms.safe
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.OutputStream

class ConnectSlaveThread(val device: BluetoothDevice) : Thread() {
    private val TAG = "SLAVE"
    private var socket: BluetoothSocket? = null
    private var inputStream: DataInputStream? = null
    private var outputStream: OutputStream? = null
    private var interrupded = false
    private var loaded: MutableList<Byte> = mutableListOf()
    private val bufferSize = 1024
    private var fileSize: Int = 0
    var listener: (Float) -> Unit = {}
    var fileListener: (ByteArray) -> Unit = {}

    init {
        safe {
            socket = device.createInsecureRfcommSocketToServiceRecord(APP_UUID)
            inputStream = DataInputStream(BufferedInputStream(socket!!.inputStream, bufferSize))
            outputStream = socket!!.outputStream
        }
    }

    override fun run() {
        super.run()
        safe {
            socket?.let {
                socket!!.connect()
                Log.e(TAG, "connected to: ${device.name}")
                val buffer = ByteArray(bufferSize)
                while (!interrupded) {
                    if (fileSize == 0) {
                        fileSize = inputStream!!.readInt()
                        Log.e(TAG, "file size : $fileSize")
                    } else {
                        val ba = inputStream!!.read(buffer)
                        loaded.addAll(buffer.toList().subList(0,ba))
                        listener((loaded.size).toFloat().div(fileSize.toFloat()).times(100))
                        if(loaded.size == fileSize){
                            Log.e("TAG","COMPLETED")
                            break
                        }
                    }

                }
                fileListener(loaded.toByteArray())
                Log.e(TAG, "loaded file paths: ${loaded.size}")
            }
        }
        cancel()

    }

    override fun interrupt() {
        super.interrupt()
        interrupded = true
        Log.e(TAG, "interrupted")
    }

    fun cancel() {
        safe {
            Log.e(TAG, "close socket")
            socket?.close()
        }
    }
}