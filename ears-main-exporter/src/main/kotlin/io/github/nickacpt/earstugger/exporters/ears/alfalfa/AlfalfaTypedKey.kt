package io.github.nickacpt.earstugger.exporters.ears.alfalfa

import io.github.nickacpt.earstugger.exporters.ears.alfalfa.codecs.AlfalfaCodec

data class AlfalfaTypedKey<T>(val key: String, val codec: AlfalfaCodec<T>)