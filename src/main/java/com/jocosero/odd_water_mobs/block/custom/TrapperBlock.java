package com.jocosero.odd_water_mobs.block.custom;

import com.jocosero.odd_water_mobs.block.entity.ModBlockEntities;
import com.jocosero.odd_water_mobs.block.entity.TrapperBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
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
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty HAS_ENTITY = BlockStateProperties.CAN_SUMMON;

    public TrapperBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.SOUTH)
                .setValue(POWERED, Boolean.FALSE)
                .setValue(HAS_ENTITY, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, HAS_ENTITY);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        boolean isPowered = level.hasNeighborSignal(pos);
        boolean wasPowered = state.getValue(POWERED);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof TrapperBlockEntity) {
            TrapperBlockEntity trapperBlockEntity = (TrapperBlockEntity) blockEntity;
            boolean hasEntity = trapperBlockEntity.hasEntity();
            state = state.setValue(HAS_ENTITY, hasEntity);

            if (isPowered && !wasPowered) {
                level.setBlock(pos, state.setValue(POWERED, Boolean.TRUE), 3);
                if (hasEntity) {
                    releaseEntity(level, pos, state);
                } else {
                    trapEntity(level, pos, state);
                }
            } else if (!isPowered && wasPowered) {
                level.setBlock(pos, state.setValue(POWERED, Boolean.FALSE), 3);
            }
        }

        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL_IMMEDIATE); // Ensure immediate update
        level.updateNeighborsAt(pos, this); // Notify neighbors
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
    }

    private void trapEntity(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos frontPos = pos.relative(direction);
        AABB box = new AABB(frontPos);

        List<PathfinderMob> entities = level.getEntitiesOfClass(PathfinderMob.class, box, e -> e.getBbWidth() < 1.0F && e.getBbHeight() < 1.0F);
        if (!entities.isEmpty()) {
            PathfinderMob entity = entities.get(0);
            CompoundTag entityData = new CompoundTag();
            entity.save(entityData);

            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TrapperBlockEntity) {
                TrapperBlockEntity trapperBlockEntity = (TrapperBlockEntity) blockEntity;
                if (!trapperBlockEntity.hasEntity()) {
                    trapperBlockEntity.trapEntity(entityData);
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL_IMMEDIATE);
                }
            }
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
                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL_IMMEDIATE);
                }
            }
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TrapperBlockEntity trapperBlockEntity) {
                ItemStack itemStack = new ItemStack(this);
                if (trapperBlockEntity.hasEntity()) {
                    CompoundTag entityData = new CompoundTag();
                    entityData.put("EntityData", trapperBlockEntity.getEntityData());
                    BlockItem.setBlockEntityData(itemStack, trapperBlockEntity.getType(), entityData);
                }

                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null) {
            level.setBlock(pos, state.setValue(FACING, placer.getDirection().getOpposite()), 2);
        }
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof TrapperBlockEntity trapperBlockEntity) {
            CompoundTag entityData = BlockItem.getBlockEntityData(stack);
            if (entityData != null && entityData.contains("EntityData")) {
                trapperBlockEntity.setEntityData(entityData.getCompound("EntityData"));
                level.setBlock(pos, state.setValue(HAS_ENTITY, Boolean.TRUE), 3);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable BlockGetter pBlockGetter, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pBlockGetter, pTooltip, pFlag);
        CompoundTag blockEntityTag = BlockItem.getBlockEntityData(pStack);
        if (blockEntityTag != null && blockEntityTag.contains("EntityData")) {
            CompoundTag entityData = blockEntityTag.getCompound("EntityData");
            if (pBlockGetter instanceof Level) {
                Level pLevel = (Level) pBlockGetter;
                Entity entity = EntityType.loadEntityRecursive(entityData, pLevel, (e) -> e);
                if (entity != null) {
                    String entityName = entity.getDisplayName().getString();
                    Component combinedTooltip = Component.translatable("tooltip.trapper_block.contains").withStyle(ChatFormatting.GRAY)
                            .append(Component.literal(" " + entityName).withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
                    pTooltip.add(combinedTooltip);
                }
            }
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
        return createTickerHelper(type, ModBlockEntities.TRAPPER_BLOCK_ENTITY.get(), level.isClientSide ? TrapperBlockEntity::clientTick : TrapperBlockEntity::serverTick);
    }
}