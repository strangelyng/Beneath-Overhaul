package net.strangelyng.beneathoverhaul.common.blocks;

import net.dries007.tfc.common.Lore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.rock.RockDisplayCategory;
import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;
import java.util.function.Supplier;

public enum BeneathOverhaulRock implements RegistryRock {
    DEEPSLATE(RockDisplayCategory.METAMORPHIC, MapColor.DEEPSLATE), // Migmatite
    BLACKSTONE(RockDisplayCategory.MAFIC_IGNEOUS_INTRUSIVE, MapColor.COLOR_BLACK), // Pyroxenite
    DRIPSTONE(RockDisplayCategory.SEDIMENTARY, MapColor.TERRACOTTA_BROWN), // Travertine
    NETHERRACK(RockDisplayCategory.INTERMEDIATE_IGNEOUS_EXTRUSIVE, MapColor.NETHER); // Scoria

    public static final BeneathOverhaulRock[] VALUES = values();

    private final String serializedName;
    private final RockDisplayCategory category;
    private final MapColor color;

    BeneathOverhaulRock(RockDisplayCategory category, MapColor color) {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.color = color;
    }

    public Item.Properties createItemProperties() {
        return new Item.Properties().component(Lore.TYPE, Lore.ROCK_DISPLAY_CATEGORIES.get(category));
    }

    @Override
    public RockDisplayCategory displayCategory() {
        return category;
    }

    @Override
    public MapColor color() {
        return color;
    }

    @Override
    public Supplier<? extends Block> getBlock(Rock.BlockType type) {
        return BeneathOverhaulBlocks.ROCK_BLOCKS.get(this).get(type);
    }

    @Override
    public Supplier<? extends Block> getAnvil() {
        return null;
    }

    @Override
    public Supplier<? extends SlabBlock> getSlab(Rock.BlockType type) {
        return BeneathOverhaulBlocks.ROCK_DECORATIONS.get(this).get(type).slab();
    }

    @Override
    public Supplier<? extends StairBlock> getStair(Rock.BlockType type) {
        return BeneathOverhaulBlocks.ROCK_DECORATIONS.get(this).get(type).stair();
    }

    @Override
    public Supplier<? extends WallBlock> getWall(Rock.BlockType type) {
        return BeneathOverhaulBlocks.ROCK_DECORATIONS.get(this).get(type).wall();
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
