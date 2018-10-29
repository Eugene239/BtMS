package ru.eupavlov.btms

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.eupavlov.btms.di.sessionModule

open class MainApplication : Application(){
    val TAG = javaClass::getSimpleName

    override fun onCreate() {
        super.onCreate()
        startKoin(listOf(sessionModule))
    }
}