package ru.eupavlov.btms.mvp.btlist

import com.arellomobile.mvp.MvpView
import ru.eupavlov.btms.model.BtDevice

interface BtView : MvpView {
    fun update(devices: List<BtDevice>)
    fun showError(e:Throwable)
}