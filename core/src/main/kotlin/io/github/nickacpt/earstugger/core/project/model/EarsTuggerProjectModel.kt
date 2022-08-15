package io.github.nickacpt.earstugger.core.project.model

import io.github.nickacpt.earstugger.core.project.model.customization.EarsCustomizationModel
import io.github.nickacpt.earstugger.core.project.model.customization.ProtrusionsCustomizationModel
import io.github.nickacpt.earstugger.core.project.model.customization.TailCustomizationModel
import io.github.nickacpt.earstugger.core.project.model.customization.WingsCustomizationModel
import io.github.nickacpt.earstugger.core.project.model.customization.SnoutCustomizationModel
import io.github.nickacpt.earstugger.core.project.model.data.EraseRegionData
import java.nio.file.Path

data class EarsTuggerProjectModel(
    val inputs: List<Path>,
    val output: Path,

    var cape: Path? = null,

    val ears: EarsCustomizationModel = EarsCustomizationModel(),
    var wings: WingsCustomizationModel = WingsCustomizationModel(),
    var tail: TailCustomizationModel = TailCustomizationModel(),
    var protrusions: ProtrusionsCustomizationModel = ProtrusionsCustomizationModel(),
    var snout: SnoutCustomizationModel? = null,

    var eraseRegions: List<EraseRegionData> = listOf(),
)

