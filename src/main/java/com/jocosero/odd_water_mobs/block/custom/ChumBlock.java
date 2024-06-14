package com.jocosero.odd_water_mobs.block.custom;

import com.jocosero.odd_water_mobs.item.ModItems;
import com.jocosero.odd_water_mobs.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;;

public class ChumBlock extends FallingBlock implements BucketPickup {
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    protected static final VoxelShape[] SHAPES_BY_LAYER = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    public ChumBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 8));
    }

    public static void trySpawnDripParticles(Level pLevel, BlockPos pPos, BlockState pState) {
        VoxelShape voxelshape = pState.getCollisionShape(pLevel, pPos);
        spawnParticle(pLevel, pPos, voxelshape, (double) pPos.getY() - 0.05D);
    }

    public static void spawnParticle(Level pLevel, BlockPos pPos, VoxelShape pShape, double pY) {
        spawnFluidParticle(pLevel, (double) pPos.getX() + pShape.min(Direction.Axis.X), (double) pPos.getX() + pShape.max(Direction.Axis.X), (double) pPos.getZ() + pShape.min(Direction.Axis.Z), (double) pPos.getZ() + pShape.max(Direction.Axis.Z), pY);
    }

    public static void spawnFluidParticle(Level pParticleData, double pX1, double pX2, double pZ1, double pZ2, double pY) {
        pParticleData.addParticle(ModParticles.CHUM_CHUNKS.get(), Mth.lerp(pParticleData.random.nextDouble(), pX1, pX2), pY, Mth.lerp(pParticleData.random.nextDouble(), pZ1, pZ2), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 11);
        if (!pLevel.isClientSide()) {
            pLevel.levelEvent(2001, pPos, Block.getId(pState));
        }

        return new ItemStack(ModItems.CHUM_BUCKET.get());
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.NETHER_WART_BREAK);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_LAYER[state.getValue(LAYERS) - 1];
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return SHAPES_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
    }

//    @Override
//    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
//        BlockState blockstate = world.getBlockState(pos.below());
//        if (blockstate.is(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
//            return false;
//        } else if (blockstate.is(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)) {
//            return true;
//        } else {
//            return Block.isFaceFull(blockstate.getCollisionShape(world, pos.below()), Direction.UP) || blockstate.getBlock() == this && blockstate.getValue(LAYERS) == 8;
//        }
//    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        return !state.canSurvive(world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.getBlock() == this) {
            int i = blockstate.getValue(LAYERS);
            return blockstate.setValue(LAYERS, Math.min(8, i + 1));
        } else {
            return super.getStateForPlacement(context);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        trySpawnDripParticles(pLevel, pPos, pState);
    }
}
