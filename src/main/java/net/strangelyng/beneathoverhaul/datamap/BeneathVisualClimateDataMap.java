package net.strangelyng.beneathoverhaul.datamap;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;

@EventBusSubscriber(modid = BeneathOverhaul.MOD_ID)
public class BeneathVisualClimateDataMap {
    public static final DataMapType<Biome, BeneathVisualClimateData> BENEATH_VISUAL_CLIMATE_DATA = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(BeneathOverhaul.MOD_ID, "beneath_visual_climate_data"),
            Registries.BIOME,
            BeneathVisualClimateData.CODEC
    ).synced(BeneathVisualClimateData.CODEC, false).build();

    @SubscribeEvent
    public static void register(RegisterDataMapTypesEvent event) {
        event.register(BENEATH_VISUAL_CLIMATE_DATA);
    }
}
