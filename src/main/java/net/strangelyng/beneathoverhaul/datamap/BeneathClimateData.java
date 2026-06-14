package net.strangelyng.beneathoverhaul.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BeneathClimateData(float temperature, float rainfall, float visualTemperature, float visualRainfall) {
    public static final Codec<BeneathClimateData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("temperature").forGetter(BeneathClimateData::temperature),
            Codec.FLOAT.fieldOf("rainfall").forGetter(BeneathClimateData::rainfall),
            Codec.FLOAT.fieldOf("visualTemperature").forGetter(BeneathClimateData::visualTemperature),
            Codec.FLOAT.fieldOf("visualRainfall").forGetter(BeneathClimateData::visualRainfall)
    ).apply(instance, BeneathClimateData::new));
}