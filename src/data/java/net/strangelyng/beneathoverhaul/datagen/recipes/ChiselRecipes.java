package net.strangelyng.beneathoverhaul.datagen.recipes;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.player.ChiselMode;
import net.dries007.tfc.common.recipes.ChiselRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;

import java.util.stream.Stream;

public interface ChiselRecipes extends Recipes {
    default void chiselRecipes() {
        Stream.of(BeneathOverhaulRock.VALUES).forEach(this::addRock);

        Stream.of(BeneathOverhaulRock.VALUES).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                if (type.hasVariants()) {
                    chisel(
                            BlockIngredient.of(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get()),
                            BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().get().defaultBlockState(),
                            ChiselMode.SLAB.get(),
                            ItemStackProvider.of(BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().asItem())
                    );

                    chisel(
                            BlockIngredient.of(BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(type).get()),
                            BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().get().defaultBlockState(),
                            ChiselMode.STAIR.get(),
                            ItemStackProvider.empty()
                    );
                }
            });
        });
    }

    private void addRock(BeneathOverhaulRock rockType) {
        final var BLOCK_MAP = BeneathOverhaulBlocks.ROCK_BLOCKS.get(rockType);

        chisel(
                BlockIngredient.of(
                    BLOCK_MAP.get(Rock.BlockType.RAW).get(),
                    BLOCK_MAP.get(Rock.BlockType.HARDENED).get()),
                BLOCK_MAP.get(Rock.BlockType.SMOOTH).get().defaultBlockState(),
                ChiselMode.SMOOTH.get(),
                ItemStackProvider.empty()
        );

        chisel(
                BlockIngredient.of(BLOCK_MAP.get(Rock.BlockType.BRICKS).get()),
                BLOCK_MAP.get(Rock.BlockType.CHISELED).get().defaultBlockState(),
                ChiselMode.SMOOTH.get(),
                ItemStackProvider.empty()
        );
    }

    private void chisel(BlockIngredient ingredient, BlockState output, ChiselMode mode, ItemStackProvider itemOutput) {
        add(new ChiselRecipe(ingredient, output, mode, itemOutput))     ;
    }
}
