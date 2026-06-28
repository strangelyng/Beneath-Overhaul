package net.strangelyng.beneathoverhaul.datagen.providers;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.items.Powder;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulOres;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;
import net.strangelyng.beneathoverhaul.datagen.builders.LootTableBuilders;

import java.util.Set;
import java.util.stream.Stream;

/*
 * Special thanks to Gourmandd, much of the datagen code is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/datagen/providers/BuiltinBlockLootTables.java
 */

public class BuiltInBlockLootTableProvider extends BlockLootSubProvider {

    protected BuiltInBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BeneathOverhaulBlocks.BLOCKS.getEntries()
                .stream().map(e -> (Block) e.value()).toList();
    }

    private void addOreTable(Block oreBlock, Item oreItem) {
        this.add(oreBlock, LootTableBuilders.createOreTable(oreBlock, oreItem));
    }

    private void addEmptyTable(Block block) {
        this.add(block, LootTableBuilders.createEmptyDropTable(block));
    }

    private void addRockBlockLootTable(BeneathOverhaulRock rock, Rock.BlockType type) {
        switch (type) {
            case LOOSE, MOSSY_LOOSE -> this.add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createLooseRockDropTable(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get()));
            case SPIKE, ROPE_ANCHOR -> this.add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createRockDropTable(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get(), 1, 2));
            case RAW, HARDENED ->
                    this.add(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createRawRockDropTable(
                        BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get(),
                        BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get()
            ));
            default -> this.dropSelf(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get());
        }
    }

    @Override
    protected void generate() {
        generateOres();
        generateRocks();
        generateMisc();
    }

    private void generateOres() {
        BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES.values().forEach(map -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    addOreTable(map.get(ore).get(), TFCItems.ORES.get(ore).get());
                }
            });
        });

        Stream.of(Ore.Grade.values()).forEach(grade -> {
            BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.values().forEach(map -> {
                Stream.of(Ore.values()).forEach(ore -> {
                    if (ore.isGraded()) {
                        addOreTable(map.get(ore).get(grade).get(), TFCItems.GRADED_ORES.get(ore).get(getTFCGrade(grade)).get());
                    }
                });
            });
        });

        BeneathOverhaulBlocks.BENEATH_ROCK_CUSTOM_ORES.values().forEach(map -> {
            Stream.of(BeneathOverhaulOres.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    addOreTable(map.get(ore).get(), BeneathOverhaulItems.ORES.get(ore).get());
                }
            });
        });

        // TODO: Custom Graded Ores

        // Compat Ores
        Stream.of(Ore.Grade.values()).forEach(grade -> {
            BeneathOverhaulBlocks.BENEATH_ROCK_CHROMITE_ORES.values().forEach(map -> {
                addEmptyTable(map.get(grade).get());
//                addOreTable(map.get(grade).get(), FLItems.CHROMIUM_ORES.get(getTFCGrade(grade)).get());
            });

            BeneathOverhaulBlocks.BENEATH_ROCK_BAUXITE_ORES.values().forEach(map -> {
                addEmptyTable(map.get(grade).get());
//                addOreTable(map.get(grade).get(), com.nmagpie.tfc_ie_addon.common.items.Items.ORES.get(IEOre.BAUXITE).get(getTFCGrade(grade)).get());
            });

            BeneathOverhaulBlocks.BENEATH_ROCK_GALENA_ORES.values().forEach(map -> {
                addEmptyTable(map.get(grade).get());
//                addOreTable(map.get(grade).get(), com.nmagpie.tfc_ie_addon.common.items.Items.ORES.get(IEOre.GALENA).get(getTFCGrade(grade)).get());
            });

            BeneathOverhaulBlocks.BENEATH_ROCK_URANINITE_ORES.values().forEach(map -> {
                addEmptyTable(map.get(grade).get());
//                addOreTable(map.get(grade).get(), com.nmagpie.tfc_ie_addon.common.items.Items.ORES.get(IEOre.URANINITE).get(getTFCGrade(grade)).get());
            });
        });
    }

    private void generateRocks() {
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                addRockBlockLootTable(rock, type);
                if ((type.hasVariants() || type == Rock.BlockType.MOSSY_COBBLE || type == Rock.BlockType.MOSSY_BRICKS)) {
                    this.dropSelf(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().get());
                    this.dropSelf(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().get());
                    this.dropSelf(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().get());
                }
            });
        });
    }

    private void generateMisc() {
        this.add(BeneathOverhaulBlocks.CHARRED_LOG.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).when(ExplosionCondition.survivesExplosion())
                                .add(LootItem.lootTableItem(Items.CHARCOAL.asItem())))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).when(ExplosionCondition.survivesExplosion())
                                .add(LootItem.lootTableItem(TFCItems.SOOT).when(LootItemRandomChanceCondition.randomChance(0.5f)))));

        this.add(BeneathOverhaulBlocks.MUSHROOM_SPROUTS.get(),
                LootTableBuilders.createShearableBlockDropTable(BeneathOverhaulBlocks.MUSHROOM_SPROUTS.get()));
        this.dropSelf(BeneathOverhaulBlocks.MUSHROOM_ROOTS.get());

        this.dropSelf(BeneathOverhaulBlocks.ASH_LAYER_BLOCK.get());

        this.add(BeneathOverhaulBlocks.ASH_LAYER_BLOCK.get(),
                LootTableBuilders.createLayerBlockDropTable(BeneathOverhaulBlocks.ASH_LAYER_BLOCK.get(), TFCItems.POWDERS.get(Powder.WOOD_ASH).get()));

        this.dropOther(BeneathOverhaulBlocks.FLY_AGARIC.get(), BeneathOverhaulItems.FLY_AGARIC.get());

        this.dropOther(BeneathOverhaulBlocks.PIGLIN_MASK.get(), BeneathOverhaulItems.PIGLIN_MASK.get());

        this.dropOther(BeneathOverhaulBlocks.BASTION_LAMP.get(), TFCBlocks.METALS.get(Metal.BLACK_BRONZE).get(Metal.BlockType.LAMP).get());
    }

    public static Ore.Grade getTFCGrade(Ore.Grade grade) {
        switch (grade) {
            case POOR -> {
                return Ore.Grade.POOR;
            }
            case NORMAL -> {
                return Ore.Grade.NORMAL;
            }
            case RICH -> {
                return Ore.Grade.RICH;
            }
            default -> {
                return Ore.Grade.NORMAL;
            }
        }
    }
}
