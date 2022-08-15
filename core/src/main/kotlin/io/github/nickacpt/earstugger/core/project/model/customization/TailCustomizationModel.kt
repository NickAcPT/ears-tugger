package io.github.nickacpt.earstugger.core.project.model.customization

import io.github.nickacpt.earstugger.core.project.model.data.EarsCustomizationTailBendsData
import io.github.nickacpt.earstugger.core.project.model.enums.TailMode

data class TailCustomizationModel(
    var tailMode: TailMode = TailMode.NONE,
    var tailSegments: Int = 1,
    var tailBends: EarsCustomizationTailBendsData? = null,
)