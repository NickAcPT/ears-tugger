package io.github.nickacpt.earstugger.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import io.github.nickacpt.earstugger.cli.commands.ApplyCommand

class EarsTuggerCommand :
    CliktCommand("EarsTugger is a tool designed to aid with the injection of the metadata in Minecraft skins for use with the amazing Ears mod") {

    init {
        subcommands(ApplyCommand())
    }

    override fun run() {
    }
}