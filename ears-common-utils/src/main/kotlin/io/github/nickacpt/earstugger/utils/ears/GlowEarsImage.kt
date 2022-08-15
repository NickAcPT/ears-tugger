package io.github.nickacpt.earstugger.utils.ears

import com.playsawdust.chipper.glow.image.ImageData
import com.unascribed.ears.common.WritableEarsImage

class GlowEarsImage(val imageData: ImageData) : WritableEarsImage {
    override fun getWidth(): Int = imageData.width

    override fun getHeight(): Int  = imageData.height

    override fun getARGB(x: Int, y: Int): Int = imageData.getPixel(x, y)

    override fun setARGB(x: Int, y: Int, argb: Int) {
        imageData.setPixel(x, y, argb)
    }

    override fun copy(): GlowEarsImage {
        return GlowEarsImage(ImageData(imageData.width, imageData.height, imageData.data.clone()))
    }
}