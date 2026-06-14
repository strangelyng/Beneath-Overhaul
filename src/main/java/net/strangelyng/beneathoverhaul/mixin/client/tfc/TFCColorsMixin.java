package net.strangelyng.beneathoverhaul.mixin.client.tfc;

import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.client.TFCColors;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.strangelyng.beneathoverhaul.client.BeneathOverhaulColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TFCColors.class, remap = false)
public abstract class TFCColorsMixin {

    /* TODO: This is functional, but hacky and imperfect
     * Refactor, check out vanilla biome blend logic?
     * Use biome grass colors in the beneath?
     * Assign custom temp and groundwater values for texture purposes using datamap?
     */
    @Inject(method = "getClimateColor([ILnet/minecraft/core/BlockPos;)I", at = @At("HEAD"), cancellable = true)
    private static void beneathOverhaul$redirectGetClimateColor(int[] colorCache, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        Level level = ClientHelpers.getLevel();
        if (level != null && level.dimension().equals(Level.NETHER)) {
            if (level.getBiome(pos).is(ResourceLocation.parse("beneathoverhaul:nether/lush_hollow"))) {
                float temperature = 26;
                float groundwater = 235;
                cir.setReturnValue(BeneathOverhaulColors.getBeneathClimateColor(colorCache, temperature, groundwater));
            } else {
                ClimateModel model = Climate.get(level);
                float temperature = model.getInstantTemperature(level, pos);
                float groundwater = model.getInstantGroundwater(level, pos);
                cir.setReturnValue(BeneathOverhaulColors.getBeneathClimateColor(colorCache, temperature, groundwater));
            }
        }
    }


}
