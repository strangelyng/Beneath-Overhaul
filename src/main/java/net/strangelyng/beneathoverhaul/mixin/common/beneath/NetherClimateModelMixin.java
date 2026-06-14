package net.strangelyng.beneathoverhaul.mixin.common.beneath;

import com.eerussianguy.beneath.misc.NetherClimateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.datamap.BeneathClimateData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.eerussianguy.beneath.misc.NetherClimateModel.altitude;
import static net.strangelyng.beneathoverhaul.datamap.BeneathClimateDataMap.BENEATH_CLIMATE_DATA;

@Mixin(value = NetherClimateModel.class, remap = false)
public abstract class NetherClimateModelMixin {

    // This overrides the true Climate data (temp and rainfall) of Beneath biomes
    @Inject(method = "getAverageTemperature", at = @At("HEAD"), cancellable = true)
    public void beneathoverhaul$getAverageTemperature(LevelReader level, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        final Holder<Biome> biome = level.getBiome(pos);

        BeneathClimateData climateData = biome.getData(BENEATH_CLIMATE_DATA);

        if (climateData != null) {
            cir.setReturnValue(climateData.temperature() + altitude(pos));
        }
    }

    @Inject(method = "getAverageRainfall", at = @At("HEAD"), cancellable = true)
    public void beneathoverhaul$getAverageRainfall(LevelReader level, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        final Holder<Biome> biome = level.getBiome(pos);

        BeneathClimateData climateData = biome.getData(BENEATH_CLIMATE_DATA);

        if (climateData != null) {
            cir.setReturnValue(climateData.rainfall());
        }
    }
}