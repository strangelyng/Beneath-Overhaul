package net.strangelyng.beneathoverhaul.common.blocks;

import net.dries007.tfc.common.blocks.ExtendedBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DecorativePlantBlock extends ExtendedBlock implements IFluidLoggable {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final FluidProperty FLUID = TFCBlockStateProperties.ALL_WATER_AND_LAVA;
    public static final VoxelShape DEFAULT_SHAPE = Block.box(3.0f, 0.0f, 3.0f, 13.0f, 7.0f, 13.0f);

    private final VoxelShape shape;
    @Nullable
    private final Holder<MobEffect> effect;

    public DecorativePlantBlock(ExtendedProperties properties, VoxelShape shape, @Nullable Holder<MobEffect> effect) {
        super(properties);
        this.shape = shape;
        this.effect = effect;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(getFluidProperty(), getFluidProperty().keyFor(Fluids.EMPTY)));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(getFluidProperty());
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = this.defaultBlockState();
        if (getFluidProperty().canContain(fluidState.getType())) {
            state = state.setValue(getFluidProperty(), getFluidProperty().keyForOrEmpty(fluidState.getType()));
        }

        state = state.setValue(FACING, context.getHorizontalDirection().getOpposite());
        return state;
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        if (fluid instanceof FlowingFluid && !getFluidProperty().canContain(fluid)) {
            return true;
        }
        return IFluidLoggable.super.canPlaceLiquid(player, level, pos, state, fluid);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (fluidStateIn.getType() instanceof FlowingFluid && !getFluidProperty().canContain(fluidStateIn.getType())) {
            level.destroyBlock(pos, true);
            level.setBlock(pos, fluidStateIn.createLegacyBlock(), Block.UPDATE_CLIENTS);
            return true;
        }
        return IFluidLoggable.super.placeLiquid(level, pos, state, fluidStateIn);
    }

    @Override
    public FluidProperty getFluidProperty() {
        return FLUID;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return IFluidLoggable.super.getFluidState(state);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var attachedBlock = pos.relative(Direction.DOWN);
        return level.getBlockState(attachedBlock).isFaceSturdy(level, attachedBlock, Direction.UP);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (!canSurvive(state, level, pos)) {
            Block.updateOrDestroy(state, Blocks.AIR.defaultBlockState(), level, pos, Block.UPDATE_ALL);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (effect != null && entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(effect, 50));
        }
    }
}
