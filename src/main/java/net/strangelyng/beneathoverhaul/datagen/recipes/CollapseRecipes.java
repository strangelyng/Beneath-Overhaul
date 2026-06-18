package net.strangelyng.beneathoverhaul.datagen.recipes;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.recipes.CollapseRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;

import java.util.ArrayList;
import java.util.stream.Stream;

/*
 * Special thanks to Gourmandd, much of the datagen code is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/datagen/recipes/CollapseRecipes.java
 */

public interface CollapseRecipes extends Recipes {
    default void collapseRecipes() {
        Stream.of(BeneathOverhaulRock.VALUES).forEach(this::addRock);

        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (ore.isGraded()) {
                    collapse(
                            BlockIngredient.of(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(Ore.Grade.RICH).get()),
                            BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(Ore.Grade.NORMAL).get().defaultBlockState()
                    );

                    collapse(
                            BlockIngredient.of(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(Ore.Grade.NORMAL).get()),
                            BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(Ore.Grade.POOR).get().defaultBlockState()
                    );
                }
            });
        });
    }

    private void addRock(BeneathOverhaulRock rockType) {
        final var BLOCK_MAP = BeneathOverhaulBlocks.ROCK_BLOCKS.get(rockType);

        ArrayList<Block> collapsesToCobble = new ArrayList<>();

        collapsesToCobble.add(BLOCK_MAP.get(Rock.BlockType.RAW).get());
        collapsesToCobble.add(BLOCK_MAP.get(Rock.BlockType.HARDENED).get());
        collapsesToCobble.add(BLOCK_MAP.get(Rock.BlockType.SMOOTH).get());
        collapsesToCobble.add(BLOCK_MAP.get(Rock.BlockType.CRACKED_BRICKS).get());

        Stream.of(Ore.values()).forEach(ore -> {
            if (ore.isGraded()) {
                collapsesToCobble.add(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(rockType).get(ore).get(Ore.Grade.POOR).get());
            }
        });

        collapse(
                BlockIngredient.of(
                        collapsesToCobble.stream()
                ),
                BLOCK_MAP.get(Rock.BlockType.COBBLE).get().defaultBlockState()
        );
    }

    private void collapse(BlockIngredient ingredient, BlockState output) {
        add(new CollapseRecipe(ingredient, output));
    }
}
