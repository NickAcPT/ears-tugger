package io.github.nickacpt.earstugger.exporters.ears

import com.playsawdust.chipper.glow.image.io.PNGImageLoader
import com.unascribed.ears.api.features.AlfalfaData
import com.unascribed.ears.api.features.EarsFeatures
import io.github.nickacpt.earstugger.core.project.model.EarsTuggerProjectModel
import io.github.nickacpt.earstugger.exporters.ears.alfalfa.AlfalfaConstants
import io.github.nickacpt.earstugger.exporters.ears.alfalfa.ManagedAlfalfaData
import java.lang.Enum.*
import kotlin.io.path.readBytes

object EarsFeaturesUtils {

    private fun createAlfalfaFromProjectModel(model: EarsTuggerProjectModel): AlfalfaData {
        val data = ManagedAlfalfaData()

        val cape = model.cape
        if (cape != null) {
            data.set(AlfalfaConstants.CAPE_KEY, PNGImageLoader.load(cape.readBytes().inputStream()))
        }

        if (model.eraseRegions.isNotEmpty()) {
            data.set(AlfalfaConstants.ERASE_REGIONS_LIST_KEY, model.eraseRegions)
        }

        return data.toAlfalfaData()
    }

    fun createEarsFeaturesFromProjectModel(model: EarsTuggerProjectModel): EarsFeatures {
        return if (model.ears.enabled) {
            // Internal but we take proper care of it
            @Suppress("DEPRECATION")
            EarsFeatures.builder().apply {
                // First, the ears
                earMode(model.ears.earMode.convert())
                earAnchor(model.ears.earAnchor.convert())

                // Then, the protrusions
                claws(model.protrusions.claws)
                horn(model.protrusions.horn)

                // Then, the tails
                tailMode(model.tail.tailMode.convert())
                tailSegments(model.tail.tailSegments)

                // Capes and Wings
                capeEnabled(model.cape != null)
                wingMode(model.wings.wingMode.convert())

                // TODO: chest

                // Finally, the alfalfa
                alfalfa(createAlfalfaFromProjectModel(model))


                val tailBends = model.tail.tailBends
                if (tailBends != null) {
                    val (firstBend, secondBend, thirdBend, fourthBend) = tailBends
                    tailBends(firstBend, secondBend, thirdBend, fourthBend)
                }


                val snout = model.snout
                if (snout != null) {
                    snoutWidth(snout.width)
                    snoutHeight(snout.height)
                    snoutDepth(snout.length)
                    snoutOffset(snout.offset)
                }

            }.build()
        } else {
            EarsFeatures.DISABLED
        }
    }
}