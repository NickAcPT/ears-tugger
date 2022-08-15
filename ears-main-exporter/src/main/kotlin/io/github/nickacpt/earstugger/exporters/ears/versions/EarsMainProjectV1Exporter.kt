package io.github.nickacpt.earstugger.exporters.ears.versions

import com.playsawdust.chipper.glow.image.ImageData
import com.playsawdust.chipper.glow.image.ImageEditor
import com.playsawdust.chipper.glow.image.io.PNGImageLoader
import com.playsawdust.chipper.glow.image.io.PNGImageWriter
import com.unascribed.ears.common.Alfalfa
import com.unascribed.ears.common.EarsCommon
import com.unascribed.ears.common.EarsFeaturesWriterV1
import io.github.nickacpt.earstugger.core.project.io.EarsTuggerProjectExporter
import io.github.nickacpt.earstugger.core.project.model.EarsTuggerProjectModel
import io.github.nickacpt.earstugger.exporters.ears.EarsFeaturesUtils
import java.io.ByteArrayInputStream
import java.nio.file.Files

object EarsMainProjectV1Exporter : EarsTuggerProjectExporter {
    override fun export(project: EarsTuggerProjectModel) {
        val earsFeatures = EarsFeaturesUtils.createEarsFeaturesFromProjectModel(project)

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
            outputEditor.drawImage(image, 0, 0)
        }

        EarsCommon.preprocessSkin(earsOutput)

        EarsFeaturesWriterV1.write(earsFeatures, earsOutput)
        Alfalfa.write(earsFeatures.alfalfa, earsOutput)

        val outputStream = project.output.toFile().outputStream()
        PNGImageWriter.write(output, outputStream)
        outputStream.close()
    }
}