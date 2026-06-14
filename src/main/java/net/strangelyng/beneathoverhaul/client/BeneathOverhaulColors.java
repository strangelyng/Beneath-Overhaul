package net.strangelyng.beneathoverhaul.client;

import net.minecraft.util.Mth;

public class BeneathOverhaulColors {
    public static int getBeneathClimateColor(int[] colorCache, float temperature, float groundwater) {
        int temperatureIndex = 255 - Mth.clamp((int)((temperature + 20.0F) * 255.0F / 50.0F), 0, 255);
        int rainfallIndex = 255 - Mth.clamp((int)(groundwater * 255.0F / 500.0F), 0, 255);
        return colorCache[temperatureIndex | rainfallIndex << 8];
    }
}
