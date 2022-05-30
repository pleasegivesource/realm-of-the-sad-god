package me.ethius.server.rotsg.world

import me.ethius.shared.ivec2
import me.ethius.shared.rotsg.world.biome.BiomeFeature
import me.ethius.shared.rotsg.world.biome.feature_data_dir

class FloweringGarden:ServerWorld("Flowering Garden") {

    init {
        this.addFeature(BiomeFeature(ivec2(-15, -15), "$feature_data_dir/FloweringGarden.dat"))
        this.texDataId = "flowering_garden_portal"
    }

}