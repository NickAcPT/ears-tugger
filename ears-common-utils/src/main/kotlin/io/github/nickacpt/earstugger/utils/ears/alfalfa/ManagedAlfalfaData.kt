package io.github.nickacpt.earstugger.utils.ears.alfalfa

import com.unascribed.ears.api.Slice
import com.unascribed.ears.api.features.AlfalfaData
import com.unascribed.ears.common.util.BitInputStream
import com.unascribed.ears.common.util.BitOutputStream
import java.io.ByteArrayOutputStream

class ManagedAlfalfaData {

    private val dataMap = mutableMapOf<AlfalfaTypedKey<in Any>, Any>()

    val data: Map<AlfalfaTypedKey<in Any>, Any>
        get() = dataMap

    fun <T : Any> contains(key: AlfalfaTypedKey<T>): Boolean {
        return dataMap.containsKey(key as AlfalfaTypedKey<*>)
    }

    @Suppress("USELESS_CAST", "UNCHECKED_CAST")
    fun <T : Any> get(key: AlfalfaTypedKey<T>): T? {
        return dataMap[key as AlfalfaTypedKey<*>] as T?
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> set(key: AlfalfaTypedKey<T>, value: T) {
        dataMap[key as AlfalfaTypedKey<in Any>] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> remove(key: AlfalfaTypedKey<T>) {
        dataMap.remove(key as AlfalfaTypedKey<in Any>)
    }

    fun toAlfalfaData(): AlfalfaData {
        val finalMap = mutableMapOf<String, Slice>()

        for ((key, value) in dataMap) {
            val bytes = ByteArrayOutputStream().use { baos ->
                BitOutputStream(baos).use { bos ->
                    key.codec.encode(value, bos)
                }
                baos.toByteArray()
            }
            finalMap[key.key] = Slice(bytes)
        }

        return AlfalfaData(AlfalfaConstants.DATA_VERSION_1, finalMap)
    }

    companion object {
        fun fromAlfalfaData(alfalfaData: AlfalfaData): ManagedAlfalfaData {
            AlfalfaConstants.init()

            val result = ManagedAlfalfaData()

            for ((key, value) in alfalfaData.data) {
                BitInputStream(value.toByteArray().inputStream()).use { bis ->
                    @Suppress("UNCHECKED_CAST") val alfalfaKey = AlfalfaTypedKey.KEYS[key] as? AlfalfaTypedKey<in Any>
                    alfalfaKey?.codec?.decode(bis)?.let { result.set(alfalfaKey, it) }
                }
            }

            return result
        }
    }
}