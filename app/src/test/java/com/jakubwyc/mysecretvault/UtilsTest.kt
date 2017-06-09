package com.jakubwyc.mysecretvault

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun testSha256() {
        val bytes = sha256("data")
        println(bytes.contentToString())
        assertArrayEquals(
                byteArrayOf(58, 110, -80, 121, 15, 57, -84, -121, -55, 79,
                        56, 86, -78, -35, 44, 93, 17, 14, 104, 17, 96, 34,
                        97, -87, -87, 35, -45, -69, 35, -83, -56, -73), bytes)
    }

}