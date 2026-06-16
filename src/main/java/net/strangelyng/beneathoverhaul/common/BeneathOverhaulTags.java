package net.strangelyng.beneathoverhaul.common;

import net.dries007.tfc.util.Helpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;

public class BeneathOverhaulTags {

    public static class Biomes {
        public static final TagKey<Biome> ALLOW_SLIME_SPAWNS = create("allow_slime_spawns");

        private static TagKey<Biome> create(String id) {
            return TagKey.create(Registries.BIOME, BeneathOverhaul.rl(id));
        }
    }
}
