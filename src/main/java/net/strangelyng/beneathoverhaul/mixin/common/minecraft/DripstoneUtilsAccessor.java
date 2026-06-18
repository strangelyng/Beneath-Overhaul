package net.strangelyng.beneathoverhaul.mixin.common.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DripstoneUtils.class)
public interface DripstoneUtilsAccessor {
    @Invoker("isEmptyOrWater")
    static boolean invokeIsEmptyOrWater(LevelAccessor level, BlockPos pos) {
        throw new AssertionError();
    }

    @Invoker("isEmptyOrWaterOrLava")
    static boolean invokeIsEmptyOrWaterOrLava(LevelAccessor levelAccessor, BlockPos pos) {
        throw new AssertionError();
    }

    @Invoker("getDripstoneHeight")
    static double invokeGetDripstoneHeight(double radius, double maxRadius, double scale, double minRadius){
        throw new AssertionError();
    }

    @Invoker("isCircleMostlyEmbeddedInStone")
    static boolean invokeIsCircleMostlyEmbeddedInStone(WorldGenLevel level, BlockPos pos, int radius){
        throw new AssertionError();
    }
}
