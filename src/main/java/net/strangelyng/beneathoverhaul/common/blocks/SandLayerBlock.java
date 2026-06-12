package net.strangelyng.beneathoverhaul.common.blocks;

import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

public class SandLayerBlock extends AbstractLayerBlock {

    public SandLayerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getSpeedFactor() {
        return 1.0f;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockState = pLevel.getBlockState(pPos.below());
        if (blockState.is(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
            return false;
        } else if (blockState.is(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)) {
            return true;
        } else if (blockState.getBlock() instanceof ILeavesBlock && pState.getValue(LAYERS) == 1) {
            return true;
        } else {
            return Block.isFaceFull(blockState.getCollisionShape(pLevel, pPos.below()), Direction.UP) || blockState.is(this) && blockState.getValue(LAYERS) == 8;
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.canSurvive(pLevel, pCurrentPos)) {
            return pState;
        } else if (pState.getValue(LAYERS) > 1) {
            final BlockState fallbackState = pState.setValue(LAYERS, 1);
            if (fallbackState.canSurvive(pLevel, pCurrentPos)) {
                return fallbackState;
            }
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {

    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockState = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockState.is(this)) {
            int i = blockState.getValue(LAYERS);
            return blockState.setValue(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
        } else {
            return super.getStateForPlacement(pContext);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
