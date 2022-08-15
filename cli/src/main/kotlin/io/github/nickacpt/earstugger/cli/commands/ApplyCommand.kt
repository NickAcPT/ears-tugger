package io.github.nickacpt.earstugger.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import io.github.nickacpt.earstugger.core.EarsTugger
import io.github.nickacpt.earstugger.exporters.ears.versions.EarsMainProjectV1Exporter

class ApplyCommand : CliktCommand("The apply command is used to apply the metadata specified in an EarsTugger project") {

    private val projectFile by argument("file", "The project file definition").path()

    override fun run() {
        val model = EarsTugger.loadProjectModel(projectFile)

         EarsMainProjectV1Exporter.export(model)
    }
}