package io.github.nickacpt.earstugger.core

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.nickacpt.earstugger.core.project.model.EarsTuggerProjectModel
import java.nio.file.Path

object EarsTugger {

    val mapper = TomlMapper.builder()
        .addModule(kotlinModule())
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        .build()

    fun loadProjectModel(model: Path): EarsTuggerProjectModel {
        return mapper.readValue(model.toFile())
    }

}