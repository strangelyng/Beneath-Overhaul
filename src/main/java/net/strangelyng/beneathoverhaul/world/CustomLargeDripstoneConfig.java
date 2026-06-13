package net.strangelyng.beneathoverhaul.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.world.Codecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CustomLargeDripstoneConfig(BlockState block, int floorToCeilingSearchRange, IntProvider columnRadius, FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio,
                                         FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness, FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind) implements FeatureConfiguration {

    public static final Codec<CustomLargeDripstoneConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.BLOCK_STATE.fieldOf("block").forGetter(c -> c.block),
            Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter(c -> c.floorToCeilingSearchRange),
            IntProvider.codec(1, 60).fieldOf("column_radius").forGetter(c -> c.columnRadius),
            FloatProvider.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter(c -> c.heightScale),
            Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(c -> c.maxColumnRadiusToCaveHeightRatio),
            FloatProvider.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(c -> c.stalactiteBluntness),
            FloatProvider.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(c -> c.stalagmiteBluntness),
            FloatProvider.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(c -> c.windSpeed),
            Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(c -> c.minRadiusForWind),
            Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(c -> c.minBluntnessForWind)
    ).apply(instance, CustomLargeDripstoneConfig::new));
}
