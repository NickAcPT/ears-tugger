package io.github.nickacpt.earstugger.utils.eraser

import com.playsawdust.chipper.glow.image.io.PNGImageLoader
import com.playsawdust.chipper.glow.image.io.PNGImageWriter
import com.unascribed.ears.common.Alfalfa
import io.github.nickacpt.earstugger.core.project.model.data.EraseRegionData
import io.github.nickacpt.earstugger.utils.ears.GlowEarsImage
import io.github.nickacpt.earstugger.utils.ears.alfalfa.AlfalfaConstants
import io.github.nickacpt.earstugger.utils.ears.alfalfa.ManagedAlfalfaData
import java.nio.file.Path
import kotlin.io.path.readBytes

object EarsEraser {
    fun erase(input: Path, output: Path, region: EraseRegionData) {
        val image = GlowEarsImage(PNGImageLoader.load(input.readBytes().inputStream()))

        val alfalfa = ManagedAlfalfaData.fromAlfalfaData(Alfalfa.read(image))
        val eraseRegions = alfalfa.get(AlfalfaConstants.ERASE_REGIONS_LIST_KEY)?.toMutableList() ?: mutableListOf()
        if (!eraseRegions.any { it == region }) {
            eraseRegions.add(region)
        }

        alfalfa.set(AlfalfaConstants.ERASE_REGIONS_LIST_KEY, eraseRegions)

        Alfalfa.write(alfalfa.toAlfalfaData(), image)

        val outputStream = output.toFile().outputStream()
        PNGImageWriter.write(image.imageData, outputStream)
        outputStream.close()
    }
}