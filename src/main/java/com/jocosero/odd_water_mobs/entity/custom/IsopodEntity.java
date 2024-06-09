package com.jocosero.odd_water_mobs.entity.custom;

import com.jocosero.odd_water_mobs.entity.AbstractSeafloorWaterMob;
import com.jocosero.odd_water_mobs.entity.ai.FindWater;
import com.jocosero.odd_water_mobs.entity.ai.LeaveWater;
import com.jocosero.odd_water_mobs.entity.ai.SeafloorWander;
import com.jocosero.odd_water_mobs.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsopodEntity extends AbstractSeafloorWaterMob {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();

    private double lastTickPosX, lastTickPosY, lastTickPosZ;
    private int idleAnimationTimeout = 0;

    public IsopodEntity(EntityType<? extends AbstractSeafloorWaterMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return IsopodEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0f)
                .add(Attributes.ARMOR, 2.2f)
                .add(Attributes.FOLLOW_RANGE, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.15f);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(0, new TemptGoal(this, 1.2f, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(1, new FindWater(this));
        this.goalSelector.addGoal(1, new LeaveWater(this));
        this.goalSelector.addGoal(2, new SeafloorWander(this, 1.0D, 150, 300));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    public void tick() {
        this.lastTickPosX = this.getX();
        this.lastTickPosY = this.getY();
        this.lastTickPosZ = this.getZ();
        if (this.isUnderWater() && isMoving()) {
            this.swimmingAnimationState.startIfStopped(this.tickCount);
        }
        if (this.level().isClientSide) {
            setupAnimationStates();
        }
        super.tick();
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.startIfStopped(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    private boolean isMoving() {
        double dx = this.getX() - this.lastTickPosX;
        double dy = this.getY() - this.lastTickPosY;
        double dz = this.getZ() - this.lastTickPosZ;
        return dx * dx + dy * dy + dz * dz > 0.001D;
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float speed;
        if (this.getPose() == Pose.STANDING && isMoving()) {
            speed = Math.min(partialTick + 6F, 1F);
        } else {
            speed = 0F;
        }
        this.walkAnimation.update(speed, 0.2F);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.15F;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.ISOPOD_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }
}
