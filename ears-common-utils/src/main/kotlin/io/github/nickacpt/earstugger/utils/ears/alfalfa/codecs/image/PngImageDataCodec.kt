package io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.image

import com.playsawdust.chipper.glow.image.ImageData
import com.playsawdust.chipper.glow.image.io.PNGImageLoader
import com.playsawdust.chipper.glow.image.io.PNGImageWriter
import com.unascribed.ears.common.util.BitInputStream
import com.unascribed.ears.common.util.BitOutputStream
import io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.AlfalfaCodec

open class PngImageDataCodec : AlfalfaCodec<ImageData> {
    override fun encode(value: ImageData, bos: BitOutputStream) {
        PNGImageWriter.write(value, bos)
    }

    override fun decode(bis: BitInputStream): ImageData {
        return PNGImageLoader.load(bis)
    }
}