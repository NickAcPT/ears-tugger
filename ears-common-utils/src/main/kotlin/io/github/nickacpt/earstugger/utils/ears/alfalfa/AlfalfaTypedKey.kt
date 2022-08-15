package io.github.nickacpt.earstugger.utils.ears.alfalfa

import io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.AlfalfaCodec

data class AlfalfaTypedKey<T : Any>(val key: String, val codec: AlfalfaCodec<T>) {

    init {
        knownKeys[key] = this
    }

    companion object {
        private val knownKeys = mutableMapOf<String, AlfalfaTypedKey<*>>()

        val KEYS: Map<String, AlfalfaTypedKey<*>>
            get() = knownKeys
    }
}