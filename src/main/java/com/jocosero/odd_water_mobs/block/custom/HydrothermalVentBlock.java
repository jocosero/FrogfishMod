package com.jocosero.odd_water_mobs.block.custom;

import com.jocosero.odd_water_mobs.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class HydrothermalVentBlock extends Block {
    private final int hotfloorDamage;

    public HydrothermalVentBlock(boolean pSpawnParticles, int pHotFloor, Properties properties) {
        super(properties);
        this.hotfloorDamage = pHotFloor;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pEntity.fireImmune() && pEntity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) pEntity)) {
            pEntity.hurt(pEntity.damageSources().hotFloor(), (float) this.hotfloorDamage);
        }

        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 2);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockPos checkPos = new BlockPos(pPos.getX(), pPos.getY() + 1, pPos.getZ());
        if (pLevel.getFluidState(checkPos).is(FluidTags.WATER)) {
            for (int i = 1; i <= 3; i++) {
                BlockPos checkPosBelow = new BlockPos(pPos.getX(), pPos.getY() - i, pPos.getZ());
                Block checkBlock = pLevel.getBlockState(checkPosBelow).getBlock();
                if (checkBlock == Blocks.MAGMA_BLOCK) {
                    boolean allVents = true;
                    for (int j = 1; j < i; j++) {
                        BlockPos ventPos = new BlockPos(pPos.getX(), pPos.getY() - j, pPos.getZ());
                        Block ventBlock = pLevel.getBlockState(ventPos).getBlock();
                        if (!(ventBlock instanceof HydrothermalVentBlock)) {
                            allVents = false;
                            break;
                        }
                    }
                    if (allVents) {
                        double d0 = (float) pPos.getX() + 0.5 + (pRandom.nextFloat() - 0.5) * 0.3;
                        double d1 = ((float) pPos.getY() + 1) + (pRandom.nextFloat() / 2);
                        double d2 = (float) pPos.getZ() + 0.5 + (pRandom.nextFloat() - 0.5) * 0.3;
                        pLevel.addParticle(ParticleTypes.BUBBLE, d0, d1, d2, 0, 0, 0);
                        if (pPos.getY() <= 40) {
                            pLevel.playLocalSound((double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.6F, false);
                            pLevel.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.02, 0.02, 0.02);
                            pLevel.addParticle(ModParticles.HYDROTHERMAL_SMOKE.get(), d0, d1, d2, 0.001, 0.10, 0.001);
                            pLevel.addParticle(ModParticles.HYDROTHERMAL_SMOKE.get(), d0, d1, d2, 0.001, 0.06, 0.001);
                            pLevel.addParticle(ModParticles.HYDROTHERMAL_SMOKE.get(), d0, d1, d2, 0.006, 0.08, 0.006);
                            pLevel.addParticle(ModParticles.HYDROTHERMAL_SMOKE.get(), d0, d1, d2, 0.001, 0.06, 0.001);
                        }
                    }
                }
            }
        }
    }
}

