package net.strangelyng.beneathoverhaul.datagen.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.strangelyng.beneathoverhaul.datagen.Accessors;

import java.util.Objects;

/*
 * Special thanks to Gourmandd, much of the datagen code is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/datagen/recipes/Recipes.java
 */

public interface Recipes extends Accessors {
    HolderLookup.Provider lookup();

    default String nameOf(Recipe<?> recipe) {
        return nameOf(recipe.getResultItem(lookup()).getItem());
    }

    default void add(Recipe<?> recipe) {
        add(nameOf(recipe), recipe);
    }

    default void add(String name, Recipe<?> recipe) {
        add(Objects.requireNonNull(BuiltInRegistries.RECIPE_TYPE.getKey(recipe.getType()), "No recipes type").getPath(), name, recipe);
    }

    void add(String prefix, String name, Recipe<?> recipe);

    void remove(String... name);
}
