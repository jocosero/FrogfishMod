package com.jocosero.odd_water_mobs.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TrapperBlockEntity extends BlockEntity {
    private CompoundTag entityData;
    private boolean hasEntity;
    private int ticks = 0;

    public TrapperBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAPPER_BLOCK_ENTITY.get(), pos, state);
        this.entityData = new CompoundTag();
        this.hasEntity = false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TrapperBlockEntity blockEntity) {
        if (blockEntity.hasEntity) {
            blockEntity.ticks++;
            if (blockEntity.ticks >= 40) {
                blockEntity.ticks = 0;
            }
        }
    }

    public void trapEntity(CompoundTag entityData) {
        this.entityData = entityData;
        this.hasEntity = true;
        setChanged();
    }

    public CompoundTag releaseEntity() {
        CompoundTag data = hasEntity ? entityData : null;
        this.entityData = new CompoundTag();
        this.hasEntity = false;
        setChanged();
        return data;
    }

    public boolean hasEntity() {
        return hasEntity;
    }

    public CompoundTag getEntityData() {
        return entityData;
    }

    public void setEntityData(CompoundTag entityData) {
        this.entityData = entityData;
        this.hasEntity = true;
        setChanged();
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (hasEntity) {
            tag.put("EntityData", entityData);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("EntityData")) {
            this.entityData = tag.getCompound("EntityData");
            this.hasEntity = true;
        } else {
            this.entityData = new CompoundTag();
            this.hasEntity = false;
        }
    }

    public boolean isShaking() {
        return hasEntity && ticks < 20;
    }
}


