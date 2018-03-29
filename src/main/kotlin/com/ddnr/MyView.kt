package com.ddnr

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.View

class MyView : View() {
    override val root = VBox()

    init {
        root.children += Button("Button: ")
        root.children += Label("Label: ")
    }
}