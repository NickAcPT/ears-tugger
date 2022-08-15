package io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs

import com.unascribed.ears.common.util.BitInputStream
import com.unascribed.ears.common.util.BitOutputStream

interface AlfalfaCodec<T> {
    fun encode(value: T, bos: BitOutputStream)

    fun decode(bis: BitInputStream): T
}