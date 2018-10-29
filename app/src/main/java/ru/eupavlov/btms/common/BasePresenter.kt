package ru.eupavlov.btms.common

import android.bluetooth.BluetoothAdapter
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import org.koin.standalone.KoinComponent

open class BasePresenter<T : MvpView> : MvpPresenter<T>(), KoinComponent {
    val TAG = javaClass.name

    init {

    }

}