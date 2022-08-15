package io.github.nickacpt.earstugger.core.project.model.data

data class EarsCustomizationTailBendsData(
    var first: Float = 0.0F,
    var second: Float = 0.0F,
    var third: Float = 0.0F,
    var fourth: Float = 0.0F,
) {
    init {
        check(first in -90.0..90.0) { "first must be between -90 and 90" }
        check(second in -90.0..90.0) { "second must be between -90 and 90" }
        check(third in -90.0..90.0) { "third must be between -90 and 90" }
        check(fourth in -90.0..90.0) { "fourth must be between -90 and 90" }
    }
}