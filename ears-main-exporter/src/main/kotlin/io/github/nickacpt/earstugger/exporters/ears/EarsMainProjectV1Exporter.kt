package io.github.nickacpt.earstugger.exporters.ears

import com.playsawdust.chipper.glow.image.ImageData
import com.playsawdust.chipper.glow.image.ImageEditor
import com.playsawdust.chipper.glow.image.io.PNGImageLoader
import com.playsawdust.chipper.glow.image.io.PNGImageWriter
import com.unascribed.ears.common.Alfalfa
import com.unascribed.ears.common.EarsCommon
import com.unascribed.ears.common.EarsFeaturesWriterV1
import io.github.nickacpt.earstugger.core.project.io.EarsTuggerProjectExporter
import io.github.nickacpt.earstugger.core.project.model.EarsTuggerProjectModel
import io.github.nickacpt.earstugger.utils.ears.EarsFeaturesExporter
import io.github.nickacpt.earstugger.utils.ears.GlowEarsImage
import java.io.ByteArrayInputStream
import java.nio.file.Files

object EarsMainProjectV1Exporter : EarsTuggerProjectExporter {
    override fun export(project: EarsTuggerProjectModel) {
        val earsFeatures = EarsFeaturesExporter.createEarsFeaturesFromProjectModel(project)

        project.inputs.forEach { input ->
            if (Files.exists(project.output) && Files.isSameFile(input, project.output)) {
                throw IllegalArgumentException("Input (${input}) and output cannot be the same")
            }
        }

        val output = ImageData(64, 64)
        val earsOutput = GlowEarsImage(output)
        val outputEditor = ImageEditor.edit(output)

        for (input in project.inputs) {
            val inputStream = ByteArrayInputStream(input.toFile().readBytes())
            val image = PNGImageLoader.load(inputStream)
            outputEditor.drawImage(image, 0, 0) { src, dst, alpha ->
                val srcAlpha = src.and(0xFF000000.toInt()).ushr(24)
                if (srcAlpha == 0) dst else src
            }
        }

        EarsCommon.preprocessSkin(earsOutput)

        if (project.ears.enabled) {
            EarsFeaturesWriterV1.write(earsFeatures, earsOutput)
            Alfalfa.write(earsFeatures.alfalfa, earsOutput)
        } else {
            Alfalfa.write(EarsFeaturesExporter.createAlfalfaFromProjectModel(project), earsOutput)
        }

        val outputStream = project.output.toFile().outputStream()
        PNGImageWriter.write(output, outputStream)
        outputStream.close()
    }
}