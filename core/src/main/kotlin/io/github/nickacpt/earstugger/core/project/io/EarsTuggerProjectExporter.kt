package io.github.nickacpt.earstugger.core.project.io

import io.github.nickacpt.earstugger.core.project.model.EarsTuggerProjectModel

interface EarsTuggerProjectExporter {

    fun export(project: EarsTuggerProjectModel)

}