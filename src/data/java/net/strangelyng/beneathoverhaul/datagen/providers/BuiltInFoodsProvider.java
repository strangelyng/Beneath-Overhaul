package net.strangelyng.beneathoverhaul.datagen.providers;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.component.food.FoodCapability;
import net.dries007.tfc.common.component.food.FoodData;
import net.dries007.tfc.common.component.food.FoodDefinition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;
import net.strangelyng.beneathoverhaul.datagen.Accessors;

import java.util.concurrent.CompletableFuture;

import static net.dries007.tfc.common.component.food.FoodData.*;

public class BuiltInFoodsProvider extends DataManagerProvider<FoodDefinition> implements Accessors {

    protected BuiltInFoodsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(FoodCapability.MANAGER, output, lookup, TerraFirmaCraft.MOD_ID);
    }

    @Override
    protected void addData(HolderLookup.Provider provider) {
        add(BeneathOverhaulItems.FLY_AGARIC, ofFood(1.0f, 2.0f, 1.5f).vegetables(1.0f));
    }

    private void add(ItemLike item, FoodData food)
    {
        add(item, food, true);
    }

    private void add(ItemLike item, FoodData food, boolean edible)
    {
        add(nameOf(item).replace("food/", ""), new FoodDefinition(Ingredient.of(item), food, edible));
    }

    private void add(TagKey<Item> tag, FoodData food, boolean edible)
    {
        add(tag.location().getPath().replace("foods/", ""), new FoodDefinition(Ingredient.of(tag), food, edible));
    }
}
