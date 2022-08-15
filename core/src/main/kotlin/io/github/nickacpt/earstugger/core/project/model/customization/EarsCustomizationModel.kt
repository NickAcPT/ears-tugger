package io.github.nickacpt.earstugger.core.project.model.customization

import io.github.nickacpt.earstugger.core.project.model.enums.EarAnchor
import io.github.nickacpt.earstugger.core.project.model.enums.EarMode

data class EarsCustomizationModel(
    var enabled: Boolean = true,

    var earMode: EarMode = EarMode.NONE,
    var earAnchor: EarAnchor = EarAnchor.FRONT,
)

