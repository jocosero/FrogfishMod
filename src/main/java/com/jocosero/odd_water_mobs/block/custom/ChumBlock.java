package com.jocosero.odd_water_mobs.block.custom;

import com.jocosero.odd_water_mobs.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class ChumBlock extends FallingBlock implements BucketPickup {
    public ChumBlock(Properties properties) {
        super(properties);
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
}
