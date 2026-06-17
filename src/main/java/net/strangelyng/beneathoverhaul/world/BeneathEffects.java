package net.strangelyng.beneathoverhaul.world;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Special thanks to the TerraFirmaGreg Team, this Class is based on their work for the TerraFirmaGreg modpack
 * https://github.com/TerraFirmaGreg-Team/Core-Modern/blob/dev/src/main/java/su/terrafirmagreg/core/world/dimension_effects/BeneathEffects.java
 *
 * This prevents the time of day from affecting fog color in the Beneath
 */

public class BeneathEffects extends DimensionSpecialEffects {
    public BeneathEffects() {
        super(Float.NaN, true, SkyType.NONE, false, true);
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 fogColor, float brightness) {
        return fogColor;
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return true;
    }

    @Override
    public @Nullable float[] getSunriseColor(float timeOfDay, float partialTicks) {
        return null;
    }
}
