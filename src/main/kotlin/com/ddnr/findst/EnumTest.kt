package com.ddnr.findst

import com.ddnr.find11st.KotlinPractice
import com.ddnr.find11st.MyView

sealed class EnumTest {
    val a = KotlinPractice.StateEnum.IDLE
    val b = KotlinPractice.StateEnum.BUSY()
    val c = KotlinPractice.StatusEnum.IDLE
    val d = KotlinPractice.StatusEnum.BUSY

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