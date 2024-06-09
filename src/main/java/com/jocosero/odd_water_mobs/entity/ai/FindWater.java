package com.jocosero.odd_water_mobs.entity.ai;

import com.jocosero.odd_water_mobs.entity.SeafloorAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Random;

public class FindWater extends Goal {
    private final PathfinderMob creature;
    private BlockPos targetPos;

    public FindWater(PathfinderMob creature) {
        this.creature = creature;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        if (!this.creature.onGround() || this.creature.level().getFluidState(this.creature.blockPosition()).is(FluidTags.WATER))
            return false;

        if (this.creature instanceof SeafloorAnimal && ((SeafloorAnimal) this.creature).shouldEnterWater()) {
            int executionChance = 30;
            if (this.creature.getTarget() != null || this.creature.getRandom().nextInt(executionChance) == 0) {
                targetPos = generateTarget();
                return targetPos != null;
            }
        }
        return false;
    }

    public void start() {
        if (targetPos != null)
            this.creature.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
    }

    public void tick() {
        if (targetPos != null)
            this.creature.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
    }

    public boolean canContinueToUse() {
        if (this.creature instanceof SeafloorAnimal && !((SeafloorAnimal) this.creature).shouldEnterWater()) {
            this.creature.getNavigation().stop();
            return false;
        }
        return !this.creature.getNavigation().isDone() && targetPos != null && !this.creature.level().getFluidState(this.creature.blockPosition()).is(FluidTags.WATER);
    }

    public BlockPos generateTarget() {
        final Random random = new Random();
        final int range = this.creature instanceof SeafloorAnimal ? ((SeafloorAnimal) this.creature).getWaterSearchRange() : 14;
        for (int i = 0; i < 15; i++) {
            BlockPos blockPos = this.creature.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
            while (this.creature.level().isEmptyBlock(blockPos) && blockPos.getY() > 1) {
                blockPos = blockPos.below();
            }

            if (this.creature.level().getFluidState(blockPos).is(FluidTags.WATER)) {
                return blockPos;
            }
        }
        return null;
    }
}
