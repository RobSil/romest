package com.robsil.mainservice.util

import com.robsil.mainservice.util.StringUtil.Companion.STRING_MINIMIZE_REGEX

class StringUtil {
    companion object {
        val STRING_MINIMIZE_REGEX = Regex("[^a-zA-Z0-9]")
    }
}

fun String.minimize(): String {
    return this.replace(STRING_MINIMIZE_REGEX, "")
}
