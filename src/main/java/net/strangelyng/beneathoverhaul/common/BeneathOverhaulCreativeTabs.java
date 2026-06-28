package net.strangelyng.beneathoverhaul.common;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulBlocks;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulDecoBlockHolder;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulOres;
import net.strangelyng.beneathoverhaul.common.blocks.BeneathOverhaulRock;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;

import java.util.Map;
import java.util.function.Supplier;

public final class BeneathOverhaulCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeneathOverhaul.MOD_ID);

    public static final Id ORES = register("ores", () -> new ItemStack(BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.get(BeneathOverhaulRock.DEEPSLATE).get(Ore.NATIVE_COPPER).get(Ore.Grade.NORMAL)), BeneathOverhaulCreativeTabs::fillOresTab);
    public static final Id ROCKS = register("rock", () -> new ItemStack(BeneathOverhaulBlocks.ROCK_BLOCKS.get(BeneathOverhaulRock.DEEPSLATE).get(Rock.BlockType.HARDENED)), BeneathOverhaulCreativeTabs::fillRocksTab);
    public static final Id MISC = register("misc", () -> new ItemStack(BeneathOverhaulBlocks.MUSHROOM_SPROUTS.get()), BeneathOverhaulCreativeTabs::fillMiscTab);

    private static void fillOresTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        for (Ore ore : Ore.values()) {
            if (ore.isGraded()) {
                BeneathOverhaulBlocks.BENEATH_ROCK_TFC_GRADED_ORES.values().forEach(map -> {
                    accept(out, map, ore, Ore.Grade.POOR);
                    accept(out, map, ore, Ore.Grade.NORMAL);
                    accept(out, map, ore, Ore.Grade.RICH);
                });
            } else {
                BeneathOverhaulBlocks.BENEATH_ROCK_TFC_ORES.values().forEach(map -> accept(out, map, ore));
            }
        }

        for (BeneathOverhaulOres ore : BeneathOverhaulOres.values()) {
            if (ore.isGraded()) {
                // TODO: Custom Graded Ores
            } else {
                BeneathOverhaulBlocks.BENEATH_ROCK_CUSTOM_ORES.values().forEach(map -> accept(out, map, ore));
                accept(out, BeneathOverhaulItems.ORES, ore);
            }

            if (ore.isGem()) {
                accept(out, BeneathOverhaulItems.GEMS, ore);
            }

            if (ore.hasPowder()) {
                accept(out, BeneathOverhaulItems.ORE_POWDERS, ore);
            }
        }

        if (ModList.get().isLoaded("firmalife")) {
            BeneathOverhaulBlocks.BENEATH_ROCK_FIRMALIFE_ORES.values().forEach(map -> {
                accept(out, map, Ore.Grade.POOR);
                accept(out, map, Ore.Grade.NORMAL);
                accept(out, map, Ore.Grade.RICH);
            });
        }
    }

    private static void fillRocksTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        for (BeneathOverhaulRock rock : BeneathOverhaulRock.VALUES) {
            for (Rock.BlockType type : new Rock.BlockType[] {
                Rock.BlockType.HARDENED,
                Rock.BlockType.RAW,
                Rock.BlockType.PRESSURE_PLATE,
                Rock.BlockType.BUTTON,
                Rock.BlockType.SPIKE,
                Rock.BlockType.COBBLE,
                Rock.BlockType.MOSSY_COBBLE,
                Rock.BlockType.BRICKS,
                Rock.BlockType.CRACKED_BRICKS,
                Rock.BlockType.MOSSY_BRICKS,
                Rock.BlockType.SMOOTH,
                Rock.BlockType.CHISELED,
                Rock.BlockType.AQUEDUCT,
                Rock.BlockType.GRAVEL,
                Rock.BlockType.LOOSE,
                Rock.BlockType.MOSSY_LOOSE,
            }) {
                accept(out, BeneathOverhaulBlocks.ROCK_BLOCKS, rock, type);
                if (type.hasVariants()) {
                    accept(out, BeneathOverhaulBlocks.ROCK_DECORATIONS.get(rock).get(type));
                }
            }
            accept(out, BeneathOverhaulItems.BRICKS, rock);
        }
    }

    private static void fillMiscTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        out.accept(BeneathOverhaulBlocks.MUSHROOM_SPROUTS.asItem());
        out.accept(BeneathOverhaulBlocks.MUSHROOM_ROOTS.asItem());
        out.accept(BeneathOverhaulItems.FLY_AGARIC);
        out.accept(BeneathOverhaulItems.PIGLIN_MASK);
    }

    // Helpers

    private static Id register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final var holder = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable(BeneathOverhaul.MOD_ID + ".creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new Id(holder, displayItems);
    }

    private static <R extends ItemLike, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1))
        {
            accept(out, map.get(key1), key2);
        }
    }

    private static <R extends ItemLike, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key));
        }
    }

    private static void accept(CreativeModeTab.Output out, BeneathOverhaulDecoBlockHolder decoration)
    {
        out.accept(decoration.stair());
        out.accept(decoration.slab());
        out.accept(decoration.wall());
    }

    public record Id(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {}
}
