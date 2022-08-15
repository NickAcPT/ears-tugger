package io.github.nickacpt.earstugger.exporters.ears

import com.unascribed.ears.api.Slice
import com.unascribed.ears.api.features.AlfalfaData
import com.unascribed.ears.api.features.EarsFeatures
import com.unascribed.ears.common.util.BitOutputStream
import io.github.nickacpt.earstugger.core.project.model.EarsTuggerProjectModel
import io.github.nickacpt.earstugger.core.project.model.data.EraseRegionData
import java.io.ByteArrayOutputStream
import java.lang.Enum.*

object EarsFeaturesUtils {

    private fun EraseRegionData.writeToAlfalfa(output: BitOutputStream) {
        output.write(6, x)
        output.write(6, y)

        output.write(5, width - 1)
        output.write(5, height - 1)
    }

    private fun createAlfalfaFromProjectModel(model: EarsTuggerProjectModel): AlfalfaData {
        val alfalfaMap = mutableMapOf<String, Slice>()

        if (model.eraseRegions.isNotEmpty()) {
            val eraserBytes = ByteArrayOutputStream()
            BitOutputStream(eraserBytes).use { bos ->
                model.eraseRegions.forEach {
                    it.writeToAlfalfa(bos)
                }
                bos.flush()
            }
            alfalfaMap["erase"] = Slice(eraserBytes.toByteArray())
        }

        return AlfalfaData(1, alfalfaMap)
    }

    fun createEarsFeaturesFromProjectModel(model: EarsTuggerProjectModel): EarsFeatures {
        return if (model.ears.enabled) {
            EarsFeatures.builder()
                .earMode(model.ears.earMode.convert())
                .earAnchor(model.ears.earAnchor.convert())
                .claws(model.protrusions.claws)
                .horn(model.protrusions.horn)
                .tailMode(model.tail.tailMode.convert())
                .tailSegments(model.tail.tailSegments)
                .wingMode(model.wings.wingMode.convert())
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