package io.github.nickacpt.earstugger.utils.ears.alfalfa

import io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.AlfalfaCodec

data class AlfalfaTypedKey<T>(val key: String, val codec: AlfalfaCodec<T>)