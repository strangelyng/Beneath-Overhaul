package net.strangelyng.beneathoverhaul.common.blocks;

import com.eerussianguy.beneath.common.blocks.NFlowerBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
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

    // Rock Blocks
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

    // Ore Blocks
    public static final Map<BeneathOverhaulRock, Map<Ore, Id<Block>>> BENEATH_ROCK_TFC_ORES = Helpers.mapOf(BeneathOverhaulRock.class, rock ->
            Helpers.mapOf(Ore.class, ore -> (!ore.isGraded() && ore.hasBlock()), ore ->
                    register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
            )
    );

    public static final Map<BeneathOverhaulRock, Map<Ore, Map<Ore.Grade, Id<Block>>>> BENEATH_ROCK_TFC_GRADED_ORES = Helpers.mapOf(BeneathOverhaulRock.class, rock ->
            Helpers.mapOf(Ore.class, Ore::isGraded, ore ->
                    Helpers.mapOf(Ore.Grade.class, grade ->
                        register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
                    )
            )
    );

    // Misc Blocks
    public static final Id<RotatedPillarBlock> CHARRED_LOG = register("charred_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(8.0f).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_BLACK)));

    public static final Id<Block> FLY_AGARIC = registerNoItem("mushroom/fly_agaric", () -> new NFlowerBlock(ExtendedProperties.of(Blocks.CRIMSON_FUNGUS)));

    public static final Id<DecorativePlantBlock> MUSHROOM_SPROUTS = register("mushroom_sprouts", () -> new DecorativePlantBlock(ExtendedProperties.of().mapColor(MapColor.NONE).sound(SoundType.NETHER_WART).noCollission(), DecorativePlantBlock.DEFAULT_SHAPE, null));
    public static final Id<DecorativePlantBlock> MUSHROOM_ROOTS = register("mushroom_roots", () -> new DecorativePlantBlock(ExtendedProperties.of().mapColor(MapColor.NONE).sound(SoundType.NETHER_WART).noCollission(), DecorativePlantBlock.DEFAULT_SHAPE, null));

    public static final Id<SandLayerBlock> ASH_LAYER_BLOCK = registerNoItem("ash_pile", () -> new SandLayerBlock(BlockBehaviour.Properties.ofFullCopy(TFCBlocks.SAND.get(SandBlockType.RED).get()).mapColor(MapColor.NONE)));

    // Helper Functions
    private static <T extends Block> Id<T> registerNoItem(String name, Supplier<T> blockSupplier) {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

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
