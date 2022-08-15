package io.github.nickacpt.earstugger.utils.ears

inline fun <reified I : Enum<I>, reified O : Enum<O>> I.convert(): O {
    val outConsts = O::class.java.enumConstants.map { it.name }
    val inConsts = I::class.java.enumConstants.map { it.name }
    if (outConsts.any { !inConsts.contains(it) }) {
        throw IllegalArgumentException("Invalid conversion from ${I::class.java.name} to ${O::class.java.name}")
    }

    return java.lang.Enum.valueOf(O::class.java, this.name)
}