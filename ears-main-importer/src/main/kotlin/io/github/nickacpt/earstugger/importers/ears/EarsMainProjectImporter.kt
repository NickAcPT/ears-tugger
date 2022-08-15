package io.github.nickacpt.earstugger.importers.ears

import com.playsawdust.chipper.glow.image.ImageData
import com.playsawdust.chipper.glow.image.io.PNGImageLoader
import com.playsawdust.chipper.glow.image.io.PNGImageWriter
import com.unascribed.ears.api.features.EarsFeatures
import com.unascribed.ears.common.Alfalfa
import com.unascribed.ears.common.EarsFeaturesParser
import com.unascribed.ears.common.EarsFeaturesWriterV1
import io.github.nickacpt.earstugger.core.EarsTugger
import io.github.nickacpt.earstugger.core.project.model.EarsTuggerProjectModel
import io.github.nickacpt.earstugger.core.project.model.customization.*
import io.github.nickacpt.earstugger.core.project.model.data.EarsCustomizationTailBendsData
import io.github.nickacpt.earstugger.utils.ears.GlowEarsImage
import io.github.nickacpt.earstugger.utils.ears.alfalfa.AlfalfaConstants
import io.github.nickacpt.earstugger.utils.ears.alfalfa.AlfalfaTypedKey
import io.github.nickacpt.earstugger.utils.ears.alfalfa.ManagedAlfalfaData
import io.github.nickacpt.earstugger.utils.ears.convert
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.outputStream
import kotlin.io.path.readBytes

object EarsMainProjectImporter {

    private const val SKIN_INPUT_FILE_NAME = "skin.png"
    private const val OUTPUT_FILE_NAME = "output.png"

    private fun ManagedAlfalfaData.getFilePath(key: AlfalfaTypedKey<*>, directory: Path, extension: String): Path? {
        return if (contains(key)) {
            directory.resolve("${key.key}.$extension")
        } else {
            null
        }
    }

    private fun createProjectModelFromEarsFeatures(features: EarsFeatures, directory: Path): EarsTuggerProjectModel {
        val managedAlfalfa = ManagedAlfalfaData.fromAlfalfaData(features.alfalfa)

        val ears =
            EarsCustomizationModel(features.enabled, features.earMode.convert(), features.earAnchor.convert())
        val protrusions = ProtrusionsCustomizationModel(features.claws, features.horn)

        val tail = TailCustomizationModel(
            features.tailMode.convert(),
            features.tailSegments,
            EarsCustomizationTailBendsData(
                features.tailBend0,
                features.tailBend1,
                features.tailBend2,
                features.tailBend3,
            )
        )

        val wings = WingsCustomizationModel(features.wingMode.convert())

        val cape = managedAlfalfa.getFilePath(
            AlfalfaConstants.CAPE_KEY,
            directory,
            "png"
        )

        val snout = SnoutCustomizationModel(
            features.snoutWidth,
            features.snoutHeight,
            features.snoutDepth,
            features.snoutOffset,
        ).takeIf { it.width > 0 && it.height > 0 && it.length > 0 }

        val eraseRegionData = managedAlfalfa.get(AlfalfaConstants.ERASE_REGIONS_LIST_KEY) ?: emptyList()

        return EarsTuggerProjectModel(
            listOf(directory.resolve(SKIN_INPUT_FILE_NAME)),
            directory.resolve(OUTPUT_FILE_NAME),
            cape, ears, wings, tail, protrusions, snout, eraseRegionData
        )
    }


    fun import(earsSkin: Path, outputDirectory: Path) {
        val image = PNGImageLoader.load(earsSkin.readBytes().inputStream())
        val skinImage = GlowEarsImage(image)
        val alfalfa = Alfalfa.read(skinImage)

        val features = EarsFeaturesParser.detect(skinImage, alfalfa) {
            GlowEarsImage(PNGImageLoader.load(it.inputStream()))
        }

        val model = createProjectModelFromEarsFeatures(features, outputDirectory)
        val managedAlfalfa = ManagedAlfalfaData.fromAlfalfaData(alfalfa)

        // Dump Alfalfa image data into files
        for ((key, value) in managedAlfalfa.data) {
            if (value is ImageData) {
                val outPath = outputDirectory.resolve("${key.key}.png").also { it.parent.createDirectories() }
                outPath.outputStream().use { PNGImageWriter.write(value, it) }
            }
        }

        val nonEarsSkinImage = skinImage.copy()
        EarsFeaturesWriterV1.write(EarsFeatures.DISABLED, nonEarsSkinImage)
        outputDirectory.resolve(SKIN_INPUT_FILE_NAME).outputStream()
            .use { PNGImageWriter.write(nonEarsSkinImage.imageData, it) }

        EarsTugger.mapper.writeValue(outputDirectory.resolve("project.toml").toFile(), model)
    }

}