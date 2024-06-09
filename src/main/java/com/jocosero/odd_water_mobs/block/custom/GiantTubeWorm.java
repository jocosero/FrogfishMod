package com.jocosero.odd_water_mobs.block.custom;

import com.jocosero.odd_water_mobs.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class GiantTubeWorm extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty IS_WORM_BASE = BooleanProperty.create("is_worm_base");
    public static final BooleanProperty IN_LAND = BooleanProperty.create("in_land");

    private static final VoxelShape BASE_SHAPE = Stream.of(
            Block.box(11, 0.0009999999999976694, 3, 15, 16.000999999999998, 7),
            Block.box(7, 0.0019999999999988916, 1, 11, 16.002, 5),
            Block.box(3, 0.0009999999999976694, 2, 7, 16.000999999999998, 6),
            Block.box(8, 0.0004999999999988347, 5, 12, 16.0005, 9),
            Block.box(4, 0, 6, 8, 16, 10),
            Block.box(2, 0.0009999999999976694, 9, 6, 16.000999999999998, 13),
            Block.box(5, 0, 11, 9, 16, 15),
            Block.box(12, 0, 7, 16, 16, 11),
            Block.box(10, 0.0009999999999976694, 10, 14, 16.000999999999998, 14),
            Block.box(7, 0.0019999999999988916, 8, 11, 16.002, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public GiantTubeWorm(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(IS_WORM_BASE, false).setValue(WATERLOGGED, false).setValue(IN_LAND, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_WORM_BASE, WATERLOGGED, IN_LAND);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos,
                                  BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
            if (facing == Direction.UP) {
                return state.setValue(IS_WORM_BASE, isWormBase(facingState));
            }
        }

        boolean isInWater = world.getFluidState(currentPos).is(FluidTags.WATER);
        boolean isAboveWater = world.getBlockState(currentPos.above()).getFluidState().isEmpty();

        return state.setValue(IN_LAND, !isInWater && isAboveWater);
    }

    public boolean isWormBase(BlockState top) {
        return top.getBlock() == ModBlocks.GIANT_TUBE_WORM.get();
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER) && fluidstate.getOwnHeight() == 8);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(IS_WORM_BASE)) {
            return BASE_SHAPE;
        } else {
            return Stream.of(
                    Block.box(11, 0.001, 3, 15, 9.001, 7),
                    Block.box(7, 0.002, 1, 11, 7.002, 5),
                    Block.box(3, 0.001, 2, 7, 11.001, 6),
                    Block.box(8, 0.0005, 5, 12, 11.000499999999999, 9),
                    Block.box(4, 0, 6, 8, 9, 10),
                    Block.box(2, 0.001, 9, 6, 7.001, 13),
                    Block.box(5, 0, 11, 9, 9, 15),
                    Block.box(12, 0, 7, 16, 7, 11),
                    Block.box(10, 0.001, 10, 14, 11.001, 14),
                    Block.box(7, 0.002, 8, 11, 13.002, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        }
    }

    @Override
    public boolean canSurvive(BlockState blockstate, LevelReader world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (((world.getBlockState(new BlockPos(x, (y - 1), z))).getBlock() == Blocks.WATER)) {
            return (false);
        }
        if (world.getBlockState(pos.above()).isAir()) {

            return (false);
        }
        return (world.getBlockState(new BlockPos(x, (y - 1), z))).getBlock() == ModBlocks.DEEP_SAND.get() ||
                (world.getBlockState(new BlockPos(x, (y - 1), z))).getBlock() == ModBlocks.GIANT_TUBE_WORM.get();
    }
}
