package net.strangelyng.beneathoverhaul.mixin.client.tfc;

import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.client.TFCColors;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TFCColors.class, remap = false)
public abstract class TFCColorsMixin {

    @Inject(method = "getClimateColor([IFF)I", at = @At("HEAD"), cancellable = true)
    private static void beneathOverhaul$redirectGetClimateColor(int[] colorCache, float temperature, float groundwater, CallbackInfoReturnable<Integer> cir) {
        final Level level = ClientHelpers.getLevel();
        if (level != null && level.dimension().equals(Level.NETHER)) {
            /* TODO: Control this value with a custom colormap, biome-based for the Beneath?
             * cir.setReturnValue(-1);
             */
        }
    }
}
