package net.strangelyng.beneathoverhaul.datagen.recipes;

import net.dries007.tfc.common.recipes.QuernRecipe;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulOres;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;

import java.util.stream.Stream;

public interface QuernRecipes extends Recipes {
    default void quernRecipes() {
        Stream.of(BeneathOverhaulOres.values()).forEach(ore -> {
            if (ore.hasPowder()) {
                quern(Ingredient.of(BeneathOverhaulItems.GEMS.get(ore), BeneathOverhaulItems.ORES.get(ore)), ItemStackProvider.of(BeneathOverhaulItems.ORE_POWDERS.get(ore), 4));
            }
        });
    }

    private void quern(Ingredient ingredient, ItemStackProvider output) {
        add(new QuernRecipe(ingredient, output));
    }
}
