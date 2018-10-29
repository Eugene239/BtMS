package ru.eupavlov.btms.mvp.transfer

import com.arellomobile.mvp.MvpView

interface TransferView: MvpView {
    fun startSlave()
    fun showNoSelectedDevice()
    fun showNoSelectedFile()
    fun updateProgress(float: Float)
    fun saveFile(byteArray: ByteArray)
}