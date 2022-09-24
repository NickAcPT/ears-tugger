package io.github.nickacpt.earstugger.utils.cli.commands

import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.RawArgument
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.types.path
import io.github.nickacpt.earstugger.core.project.model.data.EraseRegionData
import io.github.nickacpt.earstugger.utils.eraser.EarsEraser

class EraseCommand : CliktCommand(name = "erase") {

    private fun RawArgument.point() = convert {
        val split = it.split("x")
        if (split.size != 2) {
            throw BadParameterValue("Invalid format. Expected: [first]x[second]")
        }
        val first = split[0].toLong()
        val second = split[1].toLong()
        Pair(first, second)
    }


    private val input by argument("earsFile", help = "The file to encode erase data").path(
        mustExist = true,
        canBeDir = false,
        canBeFile = true
    )

    private val position by argument("position", help = "The x coordinate of the erase").point()
    private val size by argument("size", help = "The width of the erase").point()

    override fun run() {
        val region = EraseRegionData(position.first, position.second, size.first, size.second)
        EarsEraser.erase(input, input, region)
    }
}