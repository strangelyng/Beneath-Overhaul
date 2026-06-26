package net.strangelyng.beneathoverhaul.common.blocks;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.strangelyng.beneathoverhaul.util.RegistryOre;

import java.util.Locale;

public enum BeneathOverhaulOres implements RegistryOre {

    QUARTZ(Type.GEM);

    private final Type type;
    private final String serializedName;

    BeneathOverhaulOres(Type type) {
        this.type = type;
        this.serializedName = name().toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean isGraded() {
        return type == Type.GRADED;
    }

    @Override
    public boolean isGem() {
        return type == Type.GEM;
    }

    @Override
    public boolean hasPowder() {
        return type != Type.NORMAL && type != Type.ITEM_ONLY;
    }

    @Override
    public boolean hasBlock() {
        return type != Type.ITEM_ONLY;
    }

    public Block create(RegistryRock rock) {
        final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .sound(SoundType.STONE)
                .strength(rock.category().hardness(6.5f), 10)
                .requiresCorrectToolForDrops();

        return new Block(properties);
    }

    private BlockState getRockBlockState(RegistryRock rock) {
        if (rock instanceof Rock) {
            return TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).get().defaultBlockState();
        }
        if (rock instanceof BeneathOverhaulRock) {
            return BeneathOverhaulBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.RAW).get().defaultBlockState();
        }
        return Blocks.STONE.defaultBlockState();
    }

    public static Ore.Grade getTFCgrade(Grade grade) {
        switch (grade) {
            case POOR -> {
                return Ore.Grade.POOR;
            }
            case NORMAL -> {
                return Ore.Grade.NORMAL;
            }
            case RICH -> {
                return Ore.Grade.RICH;
            }
            default -> {
                return Ore.Grade.NORMAL;
            }
        }
    }

    public String getSerializedName() {
        return serializedName;
    }

    public enum Grade {
        POOR, NORMAL, RICH
    }

    enum Type {
        GRADED, NORMAL, NORMAL_WITH_POWDER, GEM, ITEM_ONLY
    }
}
