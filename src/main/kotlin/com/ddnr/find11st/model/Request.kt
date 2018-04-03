package com.ddnr.find11st.model

import io.reactivex.Flowable
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

@Root(name = "Request", strict = false)
class Request {
    @set: ElementList(name = "Arguments", required = false)
    @get: ElementList(name = "Arguments", required = false)
    var arguments: List<Argument>? = null

    @set: Element(name = "ProcessingTime")
    @get: Element(name = "ProcessingTime")
    var processingTime: String? = null

    override fun toString(): String {
        Flowable.fromArray(arguments)
                .subscribe({it-> println(it.toString())})
        return "[Request] processingTime: $processingTime, arguments: $arguments"
    }

    @Root(name = "Argument", strict = false)
    class Argument {
        @set: Attribute(name = "name")
        @get: Attribute(name = "name")
        var name: String? = null

        @set: Attribute(name = "value")
        @get: Attribute(name = "value")
        var value: String? = null

        override fun toString(): String {
            return "[Argument], name: $name, value: $value"
        }
    }
}