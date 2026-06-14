package net.strangelyng.beneathoverhaul.datagen.builders;

import net.dries007.tfc.common.blocks.rock.LooseRockBlock;
import net.dries007.tfc.util.loot.IsIsolatedCondition;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.strangelyng.beneathoverhaul.common.blocks.AbstractLayerBlock;

/*
 * Special thanks to Gourmandd, much of the datagen code is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/datagen/providers/LootTableBuilders.java
 */

public class LootTableBuilders {
    public static LootTable.Builder createOreTable(Block oreBlock, Item oreItem) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).when(ExplosionCondition.survivesExplosion())
                        .add(LootItem.lootTableItem(oreBlock).when(() -> IsIsolatedCondition.INSTANCE)
                                .otherwise(LootItem.lootTableItem(oreItem))
                        )
                );
    }

    public static LootTable.Builder createRockDropTable(Block block, int min, int max) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).when(ExplosionCondition.survivesExplosion())
                        .add(LootItem.lootTableItem(block))
                        .apply(SetItemCountFunction.setCount(new UniformGenerator(ConstantValue.exactly(min), ConstantValue.exactly(max))))
                );
    }
    public static LootTable.Builder createRawRockDropTable(Block block, Block item) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).when(ExplosionCondition.survivesExplosion())
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(block).when(() -> IsIsolatedCondition.INSTANCE),
                                LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(new UniformGenerator(ConstantValue.exactly(1), ConstantValue.exactly(4))))
                        ))
                );
    }

    public static LootTable.Builder createLooseRockDropTable(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).when(ExplosionCondition.survivesExplosion())
                        .add(LootItem.lootTableItem(block)).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(2))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LooseRockBlock.COUNT, 2))
                                        )
                        ).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(3))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LooseRockBlock.COUNT, 3))
                                        )
                        )
                );
    }

    public static LootTable.Builder createLayerBlockDropTable(Block block, Item item) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).when(ExplosionCondition.survivesExplosion())
                        .add(LootItem.lootTableItem(item)).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(2))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractLayerBlock.LAYERS, 2))
                                        )
                        ).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(3))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractLayerBlock.LAYERS, 3))
                                        )
                        ).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(4))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractLayerBlock.LAYERS, 4))
                                        )
                        ).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(5))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractLayerBlock.LAYERS, 5))
                                        )
                        ).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(6))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractLayerBlock.LAYERS, 6))
                                        )
                        ).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly(7))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractLayerBlock.LAYERS, 7))
                                        )
                        )
                );
    }
}
