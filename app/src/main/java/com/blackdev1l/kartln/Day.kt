package com.blackdev1l.kartln

import java.util.*

/**
 * Created by cristian on 6/19/16.
 *
 */
data class Day(val date: Date = Date(),
               val total : Float = 0f,
               val marks: Vector<Date> = Vector()) {
    fun mark() {marks.addElement(Date())}
}
