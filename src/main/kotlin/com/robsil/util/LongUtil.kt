package com.robsil.util

class LongUtil {
}

fun Long?.compareToOrElseThrow(another: Long?, exception: Exception): Boolean {
    if (this != another) {
        throw exception
    }

    return true
}