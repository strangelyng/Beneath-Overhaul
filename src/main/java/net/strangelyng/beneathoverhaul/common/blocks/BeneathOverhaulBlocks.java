package net.strangelyng.beneathoverhaul.common.blocks;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.strangelyng.beneathoverhaul.BeneathOverhaul;
import net.strangelyng.beneathoverhaul.common.items.BeneathOverhaulItems;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class BeneathOverhaulBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, BeneathOverhaul.MOD_ID);

    public static final Map<BeneathOverhaulRock, Map<Rock.BlockType, Id<Block>>> ROCK_BLOCKS = Helpers.mapOf(BeneathOverhaulRock.class, rock ->
            Helpers.mapOf(Rock.BlockType.class, type ->
                    register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock), rock.createItemProperties()))
    );

    public static final Map<BeneathOverhaulRock, Map<Rock.BlockType, BeneathOverhaulDecoBlockHolder>> ROCK_DECORATIONS = Helpers.mapOf(BeneathOverhaulRock.class, rock ->
            Helpers.mapOf(Rock.BlockType.class, type -> type.hasVariants() || type == Rock.BlockType.MOSSY_COBBLE || type == Rock.BlockType.MOSSY_BRICKS, type -> registerDecorations(
                    "rock/" + type.name() + "/" + rock.name(),
                    () -> type.createSlab(rock),
                    () -> type.createStairs(rock),
                    () -> type.createWall(rock),
                    rock.createItemProperties()
            ))
    );

    private static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return new Id<>(RegistrationHelpers.registerBlock(BeneathOverhaulBlocks.BLOCKS, BeneathOverhaulItems.ITEMS, name, blockSupplier, blockItemFactory));
    }

    private static <T1 extends SlabBlock, T2 extends StairBlock, T3 extends WallBlock> BeneathOverhaulDecoBlockHolder registerDecorations(String baseName, Supplier<T1> slab, Supplier<T2> stair, Supplier<T3> wall, Item.Properties properties) {
        return new BeneathOverhaulDecoBlockHolder(
                register(baseName + "_slab", slab, b -> new BlockItem(b, properties)),
                register(baseName + "_stairs", stair, b -> new BlockItem(b, properties)),
                register(baseName + "_wall", wall, b -> new BlockItem(b, properties))
        );
    }

    public record Id<T extends Block>(DeferredHolder<Block, T> holder) implements RegistryHolder<Block, T>, ItemLike {
        @Override
        public Item asItem() {
            return get().asItem();
        }
    }
}
