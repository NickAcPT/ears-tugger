package io.github.nickacpt.earstugger.core.project.model.enums

enum class Protrusions(val hasClaws: Boolean = false, val hasHorn: Boolean = false) {
    NONE,
    CLAWS(hasClaws = true),
    HORN(hasHorn = true),
    CLAWS_AND_HORN(hasClaws = true, hasHorn = true),
}