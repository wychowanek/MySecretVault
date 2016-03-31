package com.jakubwyc.mysecretvault

import java.security.MessageDigest.getInstance

/**
 * Created by Jakub Wychowaniec
 */

private val CHARS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

fun sha512(value: String): ByteArray = getInstance("SHA-512").digest(value.toByteArray())

internal fun Byte.toHexString(): String {
    val i = this.toInt()
    val char2 = CHARS[i and 0x0f]
    val char1 = CHARS[i shr 4 and 0x0f]
    return "$char1$char2"
}

internal fun ByteArray.toHexString(): String {
    val builder = StringBuilder()
    for (b in this) {
        builder.append(b.toHexString())
    }
    return builder.toString()
}

internal fun String.hexToByteArray(): ByteArray {
    val result = ByteArray(this.length / 2)
    for (idx in 0..result.size - 1) {
        val srcIdx = idx * 2
        result[idx] = ((Integer.parseInt(this[srcIdx].toString(), 16)) shl 4 or Integer.parseInt(this[srcIdx + 1].toString(), 16)).toByte()
    }
    return result
}