package com.jocosero.odd_water_mobs.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SwimToSeafloor extends RandomStrollGoal {

    public SwimToSeafloor(PathfinderMob pMob, double pSpeedModifier, int pInterval) {
        super(pMob, pSpeedModifier, pInterval);
    }

    @Nullable
    protected Vec3 getPosition() {
        Vec3 vec = DefaultRandomPos.getPos(this.mob, 10, 7);

        for(int var2 = 0; vec != null && !this.mob.getCommandSenderWorld().getBlockState(fromVec3(vec)).isPathfindable(this.mob.getCommandSenderWorld(), fromVec3(vec), PathComputationType.WATER) && var2++ < 10; vec = DefaultRandomPos.getPos(this.mob, 10, 7)) {
        }
        int yDrop = 1 + this.mob.getRandom().nextInt(3);
        if(vec != null){
            BlockPos pos = fromVec3(vec);
            while(this.mob.getCommandSenderWorld().getFluidState(pos).is(FluidTags.WATER) && this.mob.getCommandSenderWorld().getBlockState(pos).isPathfindable(this.mob.getCommandSenderWorld(), fromVec3(vec), PathComputationType.WATER) && pos.getY() > 1){
                pos = pos.below();
            }
            pos = pos.above();
            int yUp = 0;
            while(this.mob.getCommandSenderWorld().getFluidState(pos).is(FluidTags.WATER) && this.mob.getCommandSenderWorld().getBlockState(pos).isPathfindable(this.mob.getCommandSenderWorld(), fromVec3(vec), PathComputationType.WATER) && yUp < yDrop){
                pos = pos.above();
                yUp++;
            }
            return Vec3.atCenterOf(pos);
        }

        return vec;
    }

    private static BlockPos fromVec3(Vec3 vec3) {
        return new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
    }
}
