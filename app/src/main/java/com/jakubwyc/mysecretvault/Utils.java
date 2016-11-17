package com.jakubwyc.mysecretvault;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class Utils {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static byte[] sha256(String data) {
        HashFunction hf = Hashing.sha256();
        return hf.hashString(data, UTF_8).asBytes();
    }

}
