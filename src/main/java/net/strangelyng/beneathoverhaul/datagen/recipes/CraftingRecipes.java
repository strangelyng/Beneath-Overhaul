package net.strangelyng.beneathoverhaul.datagen.recipes;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulDecoBlockHolder;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;

import java.util.stream.Stream;

import static net.dries007.tfc.util.DataGenerationHelpers.Builder;

/*
 * Special thanks to Gourmandd, much of the datagen code is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/datagen/recipes/CraftingRecipes.java
 */

public interface CraftingRecipes extends Recipes {
    default void craftingRecipes() {
        Stream.of(BeneathOverhaulRock.VALUES).forEach(rockType -> {
            final var MAP = BeneathOverhaulBlocks.ROCK_BLOCKS.get(rockType);
            final var BRICK_ITEM = BeneathOverhaulItems.BRICKS.get(rockType).get();
            final var RAW_BLOCK = BeneathOverhaulBlocks.ROCK_BLOCKS.get(rockType).get(Rock.BlockType.RAW);

            recipe()
                    .input('X', BRICK_ITEM)
                    .input('M', TFCItems.MORTAR)
                    .pattern("X X", "MXM")
                    .shaped(MAP.get(Rock.BlockType.AQUEDUCT).get(), 2);
            recipe()
                    .input(Ingredient.of(
                            MAP.get(Rock.BlockType.LOOSE).get(),
                            MAP.get(Rock.BlockType.MOSSY_LOOSE).get()
                    ))
                    .inputIsPrimary(TFCTags.Items.TOOLS_CHISEL)
                    .damageInputs()
                    .shapeless(BRICK_ITEM);
            recipe().bricksWithMortar(BRICK_ITEM, MAP.get(Rock.BlockType.BRICKS).get(), 4);
            recipe().bricksWithMortar(RAW_BLOCK, MAP.get(Rock.BlockType.HARDENED).get(), 2);
            recipe()
                    .input(MAP.get(Rock.BlockType.COBBLE).get())
                    .shapeless(MAP.get(Rock.BlockType.LOOSE).get(), 4);
            recipe().to2x2(MAP.get(Rock.BlockType.LOOSE).get(), MAP.get(Rock.BlockType.COBBLE).get(), 1);
            recipe()
                    .input(MAP.get(Rock.BlockType.MOSSY_COBBLE).get())
                    .shapeless(MAP.get(Rock.BlockType.MOSSY_LOOSE).get(), 4);
            recipe().to2x2(MAP.get(Rock.BlockType.MOSSY_LOOSE).get(), MAP.get(Rock.BlockType.MOSSY_COBBLE).get(), 1);

            BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rockType).forEach((type, decorations) ->
                    addDecorations(MAP.get(type).get(), decorations));

            recipe().useTool(TFCTags.Items.TOOLS_CHISEL, BRICK_ITEM, MAP.get(Rock.BlockType.BUTTON).get());
            recipe().useTool(TFCTags.Items.TOOLS_CHISEL, MAP.get(Rock.BlockType.BRICKS).get(), MAP.get(Rock.BlockType.CHISELED).get());
            recipe().useTool(TFCTags.Items.TOOLS_CHISEL, MAP.get(Rock.BlockType.RAW).get(), MAP.get(Rock.BlockType.SMOOTH).get());
            recipe().useTool(TFCTags.Items.TOOLS_HAMMER, MAP.get(Rock.BlockType.BRICKS).get(), MAP.get(Rock.BlockType.CRACKED_BRICKS).get());

            recipe()
                    .input('C', TFCTags.Items.TOOLS_CHISEL)
                    .input('X', BRICK_ITEM)
                    .pattern(" C", "XX")
                    .damageInputs()
                    .source(0, 1)
                    .shaped(MAP.get(Rock.BlockType.PRESSURE_PLATE).get());
        });
    }

    private void addDecorations(ItemLike input, BeneathOverhaulDecoBlockHolder output) {
        recipe()
                .input('#', input)
                .pattern("###")
                .shaped(output.slab().get(), 6);
        recipe()
                .input('#', input)
                .pattern("#  ", "## ")
                .shaped(output.stair().get(), 4);
        recipe()
                .input('#', input)
                .pattern("###", "###")
                .shaped(output.wall().get(), 6);
    }

    private Builder recipe() {
        return new Builder((name, r) -> {
            if (name != null) add(name, r);
            else add(r);
        });
    }
}
