package com.jocosero.odd_water_mobs.block.custom;

import com.jocosero.odd_water_mobs.block.entity.TrapperBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class TrapperBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public TrapperBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH).setValue(POWERED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        boolean isPowered = level.hasNeighborSignal(pos);
        boolean wasPowered = state.getValue(POWERED);

        if (isPowered && !wasPowered) {
            level.setBlock(pos, state.setValue(POWERED, Boolean.TRUE), 3);
            trapEntity(level, pos, state);
        } else if (!isPowered && wasPowered) {
            level.setBlock(pos, state.setValue(POWERED, Boolean.FALSE), 3);
            releaseEntity(level, pos, state);
        }
    }

    private void trapEntity(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos frontPos = pos.relative(direction);
        AABB box = new AABB(frontPos);

        List<PathfinderMob> entities = level.getEntitiesOfClass(PathfinderMob.class, box, e -> e.getBbWidth() < 1.0F && e.getBbHeight() < 1.0F);
        if (!entities.isEmpty()) {
            PathfinderMob entity = entities.get(0);
            CompoundTag entityData = new CompoundTag();
            entity.saveWithoutId(entityData);

            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TrapperBlockEntity) {
                TrapperBlockEntity trapperBlockEntity = (TrapperBlockEntity) blockEntity;
                if (!trapperBlockEntity.hasEntity()) {
                    trapperBlockEntity.trapEntity(entityData);
                    System.out.println("Entity trapped: " + entity.getName().getString());
                    entity.remove(Entity.RemovalReason.DISCARDED);
                } else {
                    System.out.println("Trapper already contains an entity.");
                }
            } else {
                System.out.println("BlockEntity is not an instance of TrapperBlockEntity.");
            }
        } else {
            System.out.println("No entities found to trap.");
        }
    }

    private void releaseEntity(Level level, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof TrapperBlockEntity) {
            TrapperBlockEntity trapperBlockEntity = (TrapperBlockEntity) blockEntity;
            CompoundTag entityData = trapperBlockEntity.releaseEntity();
            if (entityData != null) {
                Entity entity = EntityType.loadEntityRecursive(entityData, level, (e) -> {
                    e.moveTo(Vec3.atCenterOf(pos.relative(state.getValue(FACING))));
                    return e;
                });

                if (entity != null) {
                    level.addFreshEntity(entity);
                    System.out.println("Entity released: " + entity.getName().getString());
                } else {
                    System.out.println("Entity could not be loaded from data.");
                }
            } else {
                System.out.println("No entity data to release.");
            }
        } else {
            System.out.println("BlockEntity is not an instance of TrapperBlockEntity.");
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TrapperBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : (lvl, pos, st, be) -> {
            if (be instanceof TrapperBlockEntity) {
                TrapperBlockEntity.tick(lvl, pos, st, (TrapperBlockEntity) be);
            }
        };
    }
}
