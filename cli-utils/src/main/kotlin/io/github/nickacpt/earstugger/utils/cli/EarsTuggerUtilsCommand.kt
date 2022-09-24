package io.github.nickacpt.earstugger.utils.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import io.github.nickacpt.earstugger.utils.cli.commands.EraseCommand

class EarsTuggerUtilsCommand :
    CliktCommand("EarsTugger Utils is a tool to help with miscellaneous tasks related to skins for the Ears mod.") {

    init {
        subcommands(EraseCommand())
    }

    override fun run() {
    }
}