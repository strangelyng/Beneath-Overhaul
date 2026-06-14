package net.strangelyng.beneathoverhaul.datagen.providers;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.crafting.Recipe;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.datagen.recipes.ChiselRecipes;
import net.strangelyng.beneathoverhaul.datagen.recipes.CollapseRecipes;
import net.strangelyng.beneathoverhaul.datagen.recipes.CraftingRecipes;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BuiltInRecipeProvider extends RecipeProvider implements
        CraftingRecipes,
        ChiselRecipes,
        CollapseRecipes {

    final Set<ResourceLocation> removedRecipes = new HashSet<>();
    private final CompletableFuture<?> before;
    RecipeOutput output;
    HolderLookup.Provider lookup;

    final Codec<Unit> emptyRecipeCodec = Codec.STRING.fieldOf("type")
            .codec()
            .listOf()
            .fieldOf("neoforge:conditions")
            .xmap(l -> Unit.INSTANCE, r -> List.of("neoforge:false"))
            .codec();

    public BuiltInRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, CompletableFuture<?> before) {
        super(output, lookup);
        this.before = before;
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        this.output = recipeOutput;

        craftingRecipes();
        chiselRecipes();
        collapseRecipes();
    }

    @Override
    public HolderLookup.Provider lookup() {
        return lookup;
    }

    @Override
    protected CompletableFuture<?> run(CachedOutput output, HolderLookup.Provider lookup) {
        this.lookup = lookup;
        return before.thenCompose(v -> CompletableFuture.allOf(
                super.run(output, lookup),
                CompletableFuture.allOf(removedRecipes
                        .stream()
                        .map(id -> DataProvider.saveStable(output, lookup, emptyRecipeCodec, Unit.INSTANCE, recipePathProvider.json(id)))
                        .toArray(CompletableFuture[]::new))
        ));
    }

    @Override
    public void add(String prefix, String name, Recipe<?> recipe) {
        output.accept(ResourceLocation.fromNamespaceAndPath(BeneathOverhaul.MOD_ID, (prefix + "/" + name).toLowerCase(Locale.ROOT)), recipe, null);
    }

    @Override
    public void remove(String... name) {

    }
}
