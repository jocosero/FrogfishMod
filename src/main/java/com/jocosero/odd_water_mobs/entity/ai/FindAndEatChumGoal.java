package com.jocosero.odd_water_mobs.entity.ai;

import com.jocosero.odd_water_mobs.block.custom.ChumBlock;
import com.jocosero.odd_water_mobs.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;
import java.util.Random;

public class FindAndEatChumGoal extends Goal {
    private final WaterAnimal entity;
    private final double speed;
    private BlockPos targetPos;
    private int ticksEating;

    public FindAndEatChumGoal(WaterAnimal entity, double speed) {
        this.entity = entity;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        BlockPos blockPos = findNearestChumBlock();
        if (blockPos != null) {
            this.targetPos = blockPos;
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        Path path = this.entity.getNavigation().createPath(this.targetPos, 1);
        this.entity.getNavigation().moveTo(path, this.speed);
    }

    @Override
    public boolean canContinueToUse() {
        if (this.targetPos == null) return false;
        BlockState blockState = this.entity.level().getBlockState(this.targetPos);
        return blockState.getBlock() instanceof ChumBlock;
    }

    @Override
    public void stop() {
        this.targetPos = null;
        this.ticksEating = 0;
        this.entity.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.entity.blockPosition().closerThan(this.targetPos, 1.5)) {
            if (this.ticksEating % 80 == 0) {
                consumeLayer();
            }
            this.ticksEating++;
        } else {
            Path path = this.entity.getNavigation().createPath(this.targetPos, 1);
            this.entity.getNavigation().moveTo(path, this.speed);
        }
    }


    private BlockPos findNearestChumBlock() {
        BlockPos entityPos = this.entity.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(entityPos.offset(-5, -5, -5), entityPos.offset(5, 5, 5))) {
            BlockState blockState = this.entity.level().getBlockState(pos);
            if (blockState.getBlock() instanceof ChumBlock) {
                return pos;
            }
        }
        return null;
    }

    private void consumeLayer() {
        if (this.targetPos != null) {
            Level level = this.entity.level();
            BlockState blockState = level.getBlockState(this.targetPos);
            if (blockState.getBlock() instanceof ChumBlock) {
                ((ChumBlock) blockState.getBlock()).consumeLayer(level, this.targetPos);
                spawnEatingParticles();
                healNearbyEntities();
            }
        }
    }


    private void spawnEatingParticles() {
        ServerLevel serverLevel = (ServerLevel) this.entity.level();
        for (int i = 0; i < 5; i++) {
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                    this.targetPos.getX() + 0.5,
                    this.targetPos.getY() + 1.0,
                    this.targetPos.getZ() + 0.5,
                    1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    private void healNearbyEntities() {
        ServerLevel serverLevel = (ServerLevel) this.entity.level();
        for (LivingEntity livingEntity : serverLevel.getEntitiesOfClass(LivingEntity.class,
                this.entity.getBoundingBox().inflate(1))) {
            if (livingEntity instanceof WaterAnimal) {
                livingEntity.heal((float) (livingEntity.getMaxHealth() / 4));
            }
        }
    }
}

