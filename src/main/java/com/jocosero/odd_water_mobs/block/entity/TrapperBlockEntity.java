package com.jocosero.odd_water_mobs.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TrapperBlockEntity extends BlockEntity {
    private CompoundTag trappedEntityData;

    public TrapperBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAPPER_BLOCK_ENTITY.get(), pos, state);
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
        return this.trappedEntityData != null;
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

    public static void tick(Level level, BlockPos pos, BlockState state, TrapperBlockEntity blockEntity) {
        // Custom tick logic if needed
    }
}
