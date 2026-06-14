package net.strangelyng.beneathoverhaul.mixin.client.tfc;

import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.client.TFCColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.strangelyng.beneathoverhaul.datamap.BeneathVisualClimateData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.strangelyng.beneathoverhaul.datamap.BeneathVisualClimateDataMap.BENEATH_VISUAL_CLIMATE_DATA;

@Mixin(value = TFCColors.class, remap = false)
public abstract class TFCColorsMixin {
    /* TODO: This is functional, but a bit hacky, biome edges have sharp transitions
     * Possible to implement biome blend logic?
     */
    @Inject(method = "getClimateColor([ILnet/minecraft/core/BlockPos;)I", at = @At("HEAD"), cancellable = true)
    private static void beneathOverhaul$redirectGetClimateColor(int[] colorCache, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        Level level = ClientHelpers.getLevel();
        if (level != null && level.dimension().equals(Level.NETHER)) {
            final Holder<Biome> biome = level.getBiome(pos);

            BeneathVisualClimateData visualClimateData = biome.getData(BENEATH_VISUAL_CLIMATE_DATA);

            if (visualClimateData != null) {
                float temperature = visualClimateData.temperature();
                float groundwater = visualClimateData.rainfall();
                cir.setReturnValue(beneathoverhaul$getVisualClimateColor(colorCache, temperature, groundwater));
            } else {
                // Forces warm & dry temps by default
                cir.setReturnValue(beneathoverhaul$getVisualClimateColor(colorCache, 35, 0));
            }
        }
    }

    private static int beneathoverhaul$getVisualClimateColor(int[] colorCache, float temperature, float groundwater)
    {
        final int temperatureIndex = 255 - Mth.clamp((int) ((temperature + 20f) * 255f / 50f), 0, 255);
        final int rainfallIndex = 255 - Mth.clamp((int) (groundwater * 255f / 500f), 0, 255);
        return colorCache[temperatureIndex | (rainfallIndex << 8)];
    }

}