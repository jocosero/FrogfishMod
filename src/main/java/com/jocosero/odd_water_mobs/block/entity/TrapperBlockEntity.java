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

    public static void tick(Level level, BlockPos pos, BlockState state, TrapperBlockEntity blockEntity) {
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

    public void trapEntity(CompoundTag entityData) {
        this.trappedEntityData = entityData;
        this.setChanged();
    }

    @Nullable
    public CompoundTag releaseEntity() {
        if (this.trappedEntityData != null) {
            CompoundTag entityData = this.trappedEntityData;
            this.trappedEntityData = null;
            this.setChanged();
            return entityData;
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
