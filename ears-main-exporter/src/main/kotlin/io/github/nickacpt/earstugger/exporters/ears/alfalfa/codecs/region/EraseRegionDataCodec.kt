package io.github.nickacpt.earstugger.exporters.ears.alfalfa.codecs.region

import com.unascribed.ears.common.util.BitInputStream
import com.unascribed.ears.common.util.BitOutputStream
import io.github.nickacpt.earstugger.core.project.model.data.EraseRegionData
import io.github.nickacpt.earstugger.exporters.ears.alfalfa.codecs.AlfalfaCodec

object EraseRegionDataCodec : AlfalfaCodec<EraseRegionData> {
    override fun encode(value: EraseRegionData, bos: BitOutputStream) {
        bos.write(6, value.x)
        bos.write(6, value.y)

        bos.write(5, value.width - 1)
        bos.write(5, value.height - 1)
    }

    override fun decode(bis: BitInputStream): EraseRegionData {
        val x = bis.read(6).toLong()
        val y = bis.read(6).toLong()

        val width = bis.read(5).toLong() + 1
        val height = bis.read(5).toLong() + 1
        return EraseRegionData(x, y, width, height)
    }

}