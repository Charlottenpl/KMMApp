package com.example.kmmapp.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object NetUtil {

    private const val BASELENGTH = 128
    private const val LOOKUPLENGTH = 64
    private const val TWENTYFOURBITGROUP = 24
    private const val EIGHTBIT = 8
    private const val SIXTEENBIT = 16
    private const val FOURBYTE = 4
    private const val SIGN = -128
    private const val PAD = '='
    private val base64Alphabet = ByteArray(BASELENGTH)
    private val lookUpBase64Alphabet = CharArray(LOOKUPLENGTH)


    init {

        for (i in 0 until BASELENGTH) {
            base64Alphabet[i] = -1
        }

        for (i in 'Z' downTo 'A') {
            base64Alphabet[i.code] = (i - 'A'.code).code.toByte()
        }

        for (i in 'z' downTo 'a') {
            base64Alphabet[i.code] = (i - 'a'.code + 26).code.toByte()
        }

        for (i in '9' downTo '0') {
            base64Alphabet[i.code] = (i - 'a'.code + 52).code.toByte()
        }

        base64Alphabet['+'.code] = 62
        base64Alphabet['/'.code] = 63
        for (i in 0 until 26) {
            lookUpBase64Alphabet[i] = ('A' + i)
        }

        var j = 0
        for (i in 26 until 52) {
            lookUpBase64Alphabet[i] = ('a' + j)
            j++
        }

        j = 0
        for (i in 52 until 62) {
            lookUpBase64Alphabet[i] = ('0' + j)
            j++
        }

        lookUpBase64Alphabet[62] = '+'
        lookUpBase64Alphabet[63] = '/'
    }

    //pay key MD5
    fun stringToMD5(string: String?): String? {
        if (string != null) {
            if (string.isNotEmpty()) {
                val hash: ByteArray
                hash = try {
                    MessageDigest.getInstance("MD5").digest(string.toByteArray(charset("UTF-8")))
                } catch (e: NoSuchAlgorithmException) {
                    e.printStackTrace()
                    return null
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                    return null
                }
                val hex = StringBuilder(hash.size * 2)
                for (b in hash) {
                    if (b.toInt() and 0xFF < 0x10) {
                        hex.append("0")
                    }
                    hex.append(Integer.toHexString(b.toInt() and 0xFF))
                }
                return hex.toString()
            }
        }
        return ""
    }


    /**
     * Encodes hex octects into MCBase64
     *
     * @param binaryData Array containing binaryData
     * @return Encoded MCBase64 array
     */
    fun encode(binaryData: ByteArray?): String? {
        if (binaryData == null) {
            return null
        }
        val lengthDataBits: Int = binaryData.size * EIGHTBIT
        if (lengthDataBits == 0) {
            return ""
        }
        val fewerThan24bits: Int =
            lengthDataBits % TWENTYFOURBITGROUP
        val numberTriplets: Int = lengthDataBits / TWENTYFOURBITGROUP
        val numberQuartet = if (fewerThan24bits != 0) numberTriplets + 1 else numberTriplets
        var encodedData: CharArray? = null
        encodedData = CharArray(numberQuartet * 4)
        var k: Byte = 0
        var l: Byte = 0
        var b1: Byte = 0
        var b2: Byte = 0
        var b3: Byte = 0
        var encodedIndex = 0
        var dataIndex = 0
        for (i in 0 until numberTriplets) {
            b1 = binaryData[dataIndex++]
            b2 = binaryData[dataIndex++]
            b3 = binaryData[dataIndex++]
            l = (b2.toInt() and 0x0f).toByte()
            k = (b1.toInt() and 0x03).toByte()
            val val1 =
                if (b1.toInt() and SIGN == 0) (b1.toInt() shr 2).toByte() else (b1.toInt() shr 2 xor 0xc0).toByte()
            val val2 =
                if (b2.toInt() and SIGN == 0) (b2.toInt() shr 4).toByte() else (b2.toInt() shr 4 xor 0xf0).toByte()
            val val3 =
                if (b3.toInt() and SIGN == 0) (b3.toInt() shr 6).toByte() else (b3.toInt() shr 6 xor 0xfc).toByte()
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(val1.toInt())
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(val2.toInt() or (k.toInt() shl 4))
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(l.toInt() shl 2 or val3.toInt())
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(b3.toInt() and 0x3f)
        }
        // form integral number of 6-bit groups
        if (fewerThan24bits == EIGHTBIT) {
            b1 = binaryData[dataIndex]
            k = (b1.toInt() and 0x03).toByte()
            val val1 =
                if (b1.toInt() and SIGN == 0) (b1.toInt() shr 2).toByte() else (b1.toInt() shr 2 xor 0xc0).toByte()
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(val1.toInt())
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(k.toInt() shl 4)
            encodedData[encodedIndex++] = PAD
            encodedData[encodedIndex++] = PAD
        } else if (fewerThan24bits == SIXTEENBIT) {
            b1 = binaryData[dataIndex]
            b2 = binaryData[dataIndex + 1]
            l = (b2.toInt() and 0x0f).toByte()
            k = (b1.toInt() and 0x03).toByte()
            val val1 =
                if (b1.toInt() and SIGN == 0) (b1.toInt() shr 2).toByte() else (b1.toInt() shr 2 xor 0xc0).toByte()
            val val2 =
                if (b2.toInt() and SIGN == 0) (b2.toInt() shr 4).toByte() else (b2.toInt() shr 4 xor 0xf0).toByte()
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(val1.toInt())
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(val2.toInt() or (k.toInt() shl 4))
            encodedData[encodedIndex++] =
                lookUpBase64Alphabet.get(l.toInt() shl 2)
            encodedData[encodedIndex++] = PAD
        }
        return String(encodedData)
    }
}