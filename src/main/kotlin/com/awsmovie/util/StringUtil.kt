package com.awsmovie.util

object StringUtil {

    fun String.isNumeric(): Boolean =
        try {
            toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }

}