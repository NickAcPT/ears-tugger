package io.github.nickacpt.earstugger.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import io.github.nickacpt.earstugger.importers.ears.EarsMainProjectImporter

class ImportCommand : CliktCommand("The import command is used to import the metadata specified in an Ears skin into a brand new EarsTugger project") {

    val skinFile by argument(help = "The path to the Ears skin file to import")
        .path(mustExist = true, canBeDir = false)

    val outputDirectory by argument(help = "The path to the directory to output the new EarsTugger project to")
        .path()

    override fun run() {
        EarsMainProjectImporter.import(skinFile, outputDirectory)


    }


}