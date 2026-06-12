package net.strangelyng.beneathoverhaul.common.items;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class BeneathOverhaulItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, BeneathOverhaul.MOD_ID);

    public static final Map<BeneathOverhaulRock, ItemId> BRICKS = Helpers.mapOf(BeneathOverhaulRock.class, type ->
            register("brick/" + type.name(), type.createItemProperties())
    );

    private static ItemId register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static ItemId register(String name, Item.Properties properties)
    {
        return new ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), () -> new Item(properties)));
    }

    private static ItemId register(String name, Supplier<Item> item)
    {
        return new ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), item));
    }

    public record ItemId(DeferredHolder<Item, Item> holder) implements RegistryHolder<Item, Item>, ItemLike
    {
        @Override
        public Item asItem()
        {
            return get();
        }
    }
}
