package net.strangelyng.beneathoverhaul.common.blocks;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

public record BeneathOverhaulDecoBlockHolder(
    BeneathOverhaulBlocks.Id<? extends SlabBlock> slab,
    BeneathOverhaulBlocks.Id<? extends StairBlock> stair,
    BeneathOverhaulBlocks.Id<? extends WallBlock> wall
) {}