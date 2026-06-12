package net.strangelyng.beneathoverhaul.util;

import com.google.common.collect.ImmutableMap;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.Map;

public class CategoryUtils {
    public static class Ores {
        public static final Map<Ore, TagKey<Block>> TFC_ORES_TO_MINING_TIER_TAG = ImmutableMap.<Ore, TagKey<Block>>builder()
                .put(Ore.NATIVE_COPPER, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.NATIVE_GOLD, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.HEMATITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.NATIVE_SILVER, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.CASSITERITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.BISMUTHINITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.GARNIERITE, BlockTags.NEEDS_IRON_TOOL)
                .put(Ore.MALACHITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.MAGNETITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.LIMONITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.SPHALERITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.TETRAHEDRITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.GYPSUM, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.CINNABAR, BlockTags.NEEDS_IRON_TOOL)
                .put(Ore.CRYOLITE, BlockTags.NEEDS_IRON_TOOL)
                .put(Ore.BORAX, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.GRAPHITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.SALTPETER, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.SULFUR, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.SYLVITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.AMETHYST, BlockTags.NEEDS_DIAMOND_TOOL)
                .put(Ore.DIAMOND, Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .put(Ore.EMERALD, BlockTags.NEEDS_DIAMOND_TOOL)
                .put(Ore.LAPIS_LAZULI, BlockTags.NEEDS_IRON_TOOL)
                .put(Ore.OPAL, BlockTags.NEEDS_IRON_TOOL)
                .put(Ore.PYRITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.RUBY, Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .put(Ore.SAPPHIRE, Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .put(Ore.TOPAZ, BlockTags.NEEDS_DIAMOND_TOOL)
                .put(Ore.BITUMINOUS_COAL, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.LIGNITE, BlockTags.NEEDS_STONE_TOOL)
                .put(Ore.HALITE, BlockTags.NEEDS_STONE_TOOL)
                .build();
    }
}
