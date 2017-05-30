package com.jakubwyc.mysecretvault

import com.google.common.hash.Hashing
import java.nio.charset.Charset


private val UTF_8 = Charset.forName("UTF-8")

fun sha256(data: String): ByteArray {
    val hf = Hashing.sha256()
    return hf.hashString(data, UTF_8).asBytes()
}

