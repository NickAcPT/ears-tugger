package io.github.nickacpt.earstugger.utils.ears.alfalfa

import com.playsawdust.chipper.glow.image.ImageData
import io.github.nickacpt.earstugger.core.project.model.data.EraseRegionData
import io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.image.CapeImageDataCodec
import io.github.nickacpt.earstugger.utils.ears.alfalfa.codecs.region.EraseRegionDataListCodec

object AlfalfaConstants {

    const val DATA_VERSION_1: Int = 1

    private const val ERASE_KEY_NAME = "erase"
    private const val CAPE_KEY_NAME = "cape"

    @JvmStatic
    val ERASE_REGIONS_LIST_KEY: AlfalfaTypedKey<List<EraseRegionData>> = AlfalfaTypedKey(ERASE_KEY_NAME, EraseRegionDataListCodec)

    @JvmStatic
    val CAPE_KEY: AlfalfaTypedKey<ImageData> = AlfalfaTypedKey(CAPE_KEY_NAME, CapeImageDataCodec)

}