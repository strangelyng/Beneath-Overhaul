package net.strangelyng.beneathoverhaul.datagen.providers;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;
import net.strangelyng.beneathoverhaul.util.CategoryUtils;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/*
 * Special thanks to Gourmandd, much of the datagen code is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/datagen/providers/BuiltinBlockTags.java
 */

public class BuiltInBlockTagProvider extends TagsProvider<Block> {
    private final ExistingFileHelper.IResourceType resourceType;

    protected BuiltInBlockTagProvider(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(event.getGenerator().getPackOutput(), Registries.BLOCK, lookupProvider, BeneathOverhaul.MOD_ID, event.getExistingFileHelper());
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Ore Blocks
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (ore.hasBlock()) {
                    if (ore.isGraded()) {
                        addGradedOreTags(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES, ore, rock);
                    } else {
                        addOreTags(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES, ore, rock);
                    }
                }
            });
        });

        // Rock Blocks
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                if (type != Rock.BlockType.GRAVEL) {
                    if (type.hasVariants()) {
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().key());
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().key());
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().key());
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).key());
                    } else {
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).key());
                    }
                }
            });

            /*
             * this.tag(TFCTags.Blocks.CAN_COLLAPSE)
             *        .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).key())
             *        .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).key())
             *        .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).key());
             */

            this.tag(Tags.Blocks.STONES)
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).key())
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).key());

            this.tag(TFCTags.Blocks.STONES_RAW).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).key());

            /*
             * this.tag(TFCTags.Blocks.BREAKS_WHEN_ISOLATED).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).key());
             * this.tag(TFCTags.Blocks.CAN_START_COLLAPSE).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).key());
             */

            /*
             * this.tag(TFCTags.Blocks.CAN_TRIGGER_COLLAPSE)
             *        .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).key())
             *        .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).key());
             */

            this.tag(TFCTags.Blocks.STONES_SPIKE).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).key());
            this.tag(TFCTags.Blocks.STONES_HARDENED).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).key());

            this.tag(TFCTags.Blocks.CAN_LANDSLIDE)
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).key())
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE).key())
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).key());

            this.tag(Tags.Blocks.COBBLESTONES_NORMAL).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE).key());
            this.tag(Tags.Blocks.COBBLESTONES_MOSSY).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).key());

            this.tag(Tags.Blocks.COBBLESTONES)
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE).key())
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).key());

            this.tag(BlockTags.STAIRS)
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).stair().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).stair().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).stair().key());

            this.tag(BlockTags.SLABS)
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).slab().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).slab().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).slab().key());

            this.tag(BlockTags.WALLS)
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).wall().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).wall().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).wall().key());

            this.tag(BlockTags.STONE_BRICKS).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).key());

            this.tag(TFCTags.Blocks.AQUEDUCTS).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT).key());
            this.tag(Tags.Blocks.GRAVELS).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).key());
            this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).key());

            this.tag(TFCTags.Blocks.STONES_LOOSE)
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).key())
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE).key());

            this.tag(BlockTags.STONE_BRICKS)
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BRICKS).key())
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).key())
                    .add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CHISELED).key());

            this.tag(TFCTags.Blocks.STONES_SMOOTH).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SMOOTH).key());

            this.tag(BlockTags.STAIRS)
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.RAW).stair().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).stair().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).stair().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).stair().key());

            this.tag(BlockTags.SLABS)
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.RAW).slab().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).slab().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).slab().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).slab().key());

            this.tag(BlockTags.WALLS)
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.RAW).wall().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).wall().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).wall().key())
                    .add(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).wall().key());

            this.tag(BlockTags.PRESSURE_PLATES).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).key());
            this.tag(BlockTags.STONE_PRESSURE_PLATES).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).key());
            this.tag(TFCTags.Blocks.STONES_PRESSURE_PLATES).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).key());

            this.tag(BlockTags.STONE_BUTTONS).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).key());
            this.tag(BlockTags.BUTTONS).add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).key());
        });
    }

    private <T1 extends RegistryRock, T2 extends Enum> void addOreTags(Map<T1, Map<T2, BeneathOverhaulBlocks.Id<Block>>> map, T2 ore, T1 rock) {
        ResourceKey<Block> key = map.get(rock).get(ore).key();
        this.tag(getOreTierTag(ore)).add(key);
        this.tag(Tags.Blocks.ORES).add(key);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(key);
        this.tag(TFCTags.Blocks.PROSPECTABLE).add(key);
    }

    private <T1 extends RegistryRock, T2 extends Enum, T3 extends Ore.Grade> void addGradedOreTags(Map<T1, Map<T2, Map<T3, BeneathOverhaulBlocks.Id<Block>>>> map, T2 ore, T1 rock) {
        for (Ore.Grade grade : Ore.Grade.values()) {
            ResourceKey<Block> key = map.get(rock).get(ore).get(grade).key();
            this.tag(getOreTierTag(ore)).add(key);
            this.tag(Tags.Blocks.ORES).add(key);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(key);
            this.tag(TFCTags.Blocks.PROSPECTABLE).add(key);
        }
    }

    private TagKey<Block> getOreTierTag(Enum ore) {
        if (ore instanceof Ore) {
            return CategoryUtils.Ores.TFC_ORES_TO_MINING_TIER_TAG.get(ore);
        }

        return null;
    }
}
