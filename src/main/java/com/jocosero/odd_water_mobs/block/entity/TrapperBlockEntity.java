package com.jocosero.odd_water_mobs.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TrapperBlockEntity extends BlockEntity {
    private CompoundTag trappedEntityData;

    public TrapperBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAPPER_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState) {
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("TrappedEntity")) {
            trappedEntityData = tag.getCompound("TrappedEntity");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (trappedEntityData != null) {
            tag.put("TrappedEntity", trappedEntityData);
        }
    }

    public void trapEntity(PathfinderMob entity) {
        trappedEntityData = new CompoundTag();
        entity.saveWithoutId(trappedEntityData);
        entity.remove(Entity.RemovalReason.DISCARDED);
    }

    @Nullable
    public PathfinderMob releaseEntity() {
        if (trappedEntityData != null) {
            Entity entity = EntityType.loadEntityRecursive(trappedEntityData, this.level, (e) -> e);
            if (entity instanceof PathfinderMob) {
                trappedEntityData = null;
                return (PathfinderMob) entity;
            }
        }
        return null;
    }

    public boolean hasEntity() {
        return trappedEntityData != null;
    }

    @Nullable
    public Component getEntityName() {
        if (trappedEntityData != null && trappedEntityData.contains("id")) {
            return Component.literal(trappedEntityData.getString("id"));
        }
        return null;
    }
}
