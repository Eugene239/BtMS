package ru.eupavlov.btms

import java.util.*

const val BT_LIST_FRAGMENT = 1
const val FILE_FRAGMENT = 2
const val TRANSFER_FRAGMENT = 3
const val TEST_FRAGMENT = -1

val APP_UUID = UUID.fromString("e3e650a6-440d-47d6-8d54-f6e25080a455")


const val ENABLE_BT = 100
const val ENABLE_SCAN = 101
const val CHOOSE_FILE = 102
const val ENABLE_WRITE = 103
const val ENABLE_READ = 104

enum class BT_TYPE { MASTER, SLAVE }
