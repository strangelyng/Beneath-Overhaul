package net.strangelyng.beneathoverhaul.mixin.common.minecraft.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.BeneathOverhaulTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

/*
 * Allows slimes to spawn without restriction in biomes with the tag
 */

@Mixin(value = Slime.class)
public class SlimeMixin extends Mob {
    protected SlimeMixin(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @Inject(method = "checkSlimeSpawnRules", at = @At("HEAD"), cancellable = true)
    private static void beneathoverhaul$checkSlimeSpawnRules(EntityType<Slime> slime, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        if (level.getDifficulty() != Difficulty.PEACEFUL
            && level.getBiome(pos).is(BeneathOverhaulTags.Biomes.ALLOW_SLIME_SPAWNS)) {
                cir.setReturnValue(checkMobSpawnRules(slime, level, spawnType, pos, random));
        }
    }
}
