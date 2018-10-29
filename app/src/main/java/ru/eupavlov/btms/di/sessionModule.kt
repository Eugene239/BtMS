package ru.eupavlov.btms.di

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.eupavlov.btms.model.Session

val sessionModule: Module = applicationContext {
    bean { Session() }
}