package io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.region

import com.unascribed.ears.common.util.BitInputStream
import com.unascribed.ears.common.util.BitOutputStream
import io.github.nickacpt.earstugger.core.project.model.data.EraseRegionData
import io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.AlfalfaCodec

object EraseRegionDataListCodec : AlfalfaCodec<List<EraseRegionData>> {
    override fun encode(value: List<EraseRegionData>, bos: BitOutputStream) {
        for (data in value) {
            EraseRegionDataCodec.encode(data, bos)
        }
        bos.flush()
    }

    override fun decode(bis: BitInputStream): List<EraseRegionData> {
        val result = mutableListOf<EraseRegionData>()
        while (bis.available() > 0) {
            result.add(EraseRegionDataCodec.decode(bis))
        }
        return result
    }
}