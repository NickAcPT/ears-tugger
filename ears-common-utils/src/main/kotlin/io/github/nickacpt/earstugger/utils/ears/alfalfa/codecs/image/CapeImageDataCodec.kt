package io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.image

import com.playsawdust.chipper.glow.image.BlendMode
import com.playsawdust.chipper.glow.image.ImageData
import com.playsawdust.chipper.glow.image.ImageEditor
import com.unascribed.ears.common.util.BitOutputStream

object CapeImageDataCodec : PngImageDataCodec() {
    override fun encode(value: ImageData, bos: BitOutputStream) {
        super.encode(convertToEarsCapeIfNeeded(value), bos)
    }

    private fun convertToEarsCapeIfNeeded(original: ImageData): ImageData {
        if (original.height == 32) {
            val newImage = ImageData(20, 16)
            val editor = ImageEditor(newImage)
            editor.drawImage(
                /* im = */ original,
                /* destx = */ 0,
                /* desty = */ 0,
                /* srcx = */ 1,
                /* srcy = */ 1,
                /* width = */ 10,
                /* height = */ 16,
                /* mode = */ BlendMode.NORMAL,
                /* opacity = */ 1.0
            )

            editor.drawImage(
                /* im = */ original,
                /* destx = */ 10,
                /* desty = */ 0,
                /* srcx = */ 12,
                /* srcy = */ 1,
                /* width = */ 10,
                /* height = */ 16,
                /* mode = */ BlendMode.NORMAL,
                /* opacity = */ 1.0
            )

            return newImage
        }

        return original
    }
}