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
            @Suppress("DEPRECATION") // Internal but we take proper care of it
            EarsFeatures.builder()
                .earMode(model.ears.earMode.convert())
                .earAnchor(model.ears.earAnchor.convert())
                .claws(model.protrusions.claws)
                .horn(model.protrusions.horn)
                .tailMode(model.tail.tailMode.convert())
                .tailSegments(model.tail.tailSegments)
                .wingMode(model.wings.wingMode.convert())
                .capeEnabled(model.cape != null)
                .alfalfa(createAlfalfaFromProjectModel(model))
                .also {
                    val tailBends = model.tail.tailBends ?: return@also
                    val (firstBend, secondBend, thirdBend, fourthBend) = tailBends
                    it.tailBends(firstBend, secondBend, thirdBend, fourthBend)
                }
                .also {
                    val snout = model.snout ?: return@also
                    it.snoutWidth(snout.width)
                    it.snoutHeight(snout.height)
                    it.snoutDepth(snout.length)
                    it.snoutOffset(snout.offset)
                }
                .build()
        } else {
            EarsFeatures.DISABLED
        }
    }

    private inline fun <reified I : Enum<I>, reified O : Enum<O>> I.convert(): O {
        val outConsts = O::class.java.enumConstants.map { it.name }
        val inConsts = I::class.java.enumConstants.map { it.name }
        if (outConsts.any { !inConsts.contains(it) }) {
            throw IllegalArgumentException("Invalid conversion from ${I::class.java.name} to ${O::class.java.name}")
        }

        return valueOf(O::class.java, this.name)
    }

}