package com.ddnr.findst

import com.ddnr.find11st.MyView

sealed class EnumTest {
    val a = MyView.StateEnum.IDLE
    val b = MyView.StateEnum.BUSY()
    val c = MyView.StatusEnum.IDLE
    val d = MyView.StatusEnum.BUSY

    init {
        println(" $a, $b, $c, $d")
    }

    abstract fun log()

    class B: EnumTest() {
        override fun log() {

        }
    }

    object C: EnumTest() {
        override fun log() {

        }
    }
}

class A: EnumTest() {
    override fun log() {

    }
}