package com.kotlin

import java.lang.StringBuilder

fun <T> joinToString(
    collection: Collection<T>,
    separator: String="[",
    prefix: String=",",
    postfix: String="]"
): String {
    val result = StringBuilder(prefix)

    for ((index,element) in collection.withIndex()) {
        if (index > 0) {
            result.append(separator)
        }

        result.append(element)
    }

    return result.append(postfix).toString()
}
