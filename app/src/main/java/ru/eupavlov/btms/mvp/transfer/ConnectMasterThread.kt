package ru.eupavlov.btms.mvp.transfer

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import ru.eupavlov.btms.APP_UUID
import ru.eupavlov.btms.safe
import java.io.*
import java.nio.ByteBuffer

class ConnectMasterThread(private val byteArray: ByteArray) : Thread() {
    private val TAG = "MASTER"
    private var serverSocket: BluetoothServerSocket? = null
    private var socket: BluetoothSocket? = null
    private var inputStream: InputStream? = null
    private var outputStream: BufferedOutputStream? = null
    var listener: (Float) -> Unit = {}
    private var interrupded = false

    init {
        safe {
            serverSocket = BluetoothAdapter.getDefaultAdapter().listenUsingInsecureRfcommWithServiceRecord(BluetoothAdapter.getDefaultAdapter().name
                    ?: "NO_NAME", APP_UUID)
        }
    }

    override fun run() {
        super.run()
        Log.e(TAG, "STARTED")
        while (!interrupded) {
            safe {
                socket = serverSocket?.accept()
                Log.e(TAG, "connected ${socket!!.remoteDevice.name}: ${socket!!.remoteDevice.address}")
                inputStream = socket!!.inputStream
                outputStream = BufferedOutputStream(socket!!.outputStream, 1024)


                Log.e(TAG, "sending file size: ${byteArray.size}")
                outputStream!!.write(ByteBuffer.allocate(4).putInt(byteArray.size).array())
                outputStream!!.flush()
                Log.e(TAG, "writing bytes")

                val bytes = byteArray.toList().chunked(1024)

                val total = bytes.size
                bytes.withIndex().forEach {
                    outputStream!!.write(it.value.toByteArray())
                    outputStream!!.flush()
                    listener(it.index.toFloat().div(total).times(100))
                }
                listener(0f)
                outputStream!!.flush()
                serverSocket!!.close()
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
            serverSocket?.close()
        }
    }
}