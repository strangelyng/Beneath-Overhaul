package net.strangelyng.beneathoverhaul.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BeneathVisualClimateData(float temperature, float rainfall) {
    public static final Codec<BeneathVisualClimateData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("visualTemperature").forGetter(BeneathVisualClimateData::temperature),
            Codec.FLOAT.fieldOf("visualRainfall").forGetter(BeneathVisualClimateData::rainfall)
    ).apply(instance, BeneathVisualClimateData::new));
}