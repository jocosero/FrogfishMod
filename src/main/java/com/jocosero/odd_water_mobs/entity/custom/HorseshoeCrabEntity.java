package com.jocosero.odd_water_mobs.entity.custom;

import com.jocosero.odd_water_mobs.entity.AbstractSeafloorWaterMob;
import com.jocosero.odd_water_mobs.entity.ai.FindWater;
import com.jocosero.odd_water_mobs.entity.ai.LeaveWater;
import com.jocosero.odd_water_mobs.entity.ai.SeafloorWander;
import com.jocosero.odd_water_mobs.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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

public class HorseshoeCrabEntity extends AbstractSeafloorWaterMob {

    private static final EntityDataAccessor<Boolean> UPSIDE_DOWN = SynchedEntityData.defineId(HorseshoeCrabEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flippedAnimationState = new AnimationState();

    public int getUp = 80;
    public boolean hasFlipped = false;
    private double lastTickPosX, lastTickPosY, lastTickPosZ;
    private int idleAnimationTimeout = 0;

    public HorseshoeCrabEntity(EntityType<? extends AbstractSeafloorWaterMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return HorseshoeCrabEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8.0f)
                .add(Attributes.ARMOR, 2.4f)
                .add(Attributes.FOLLOW_RANGE, 1.0f)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.15f);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(UPSIDE_DOWN, false);
    }

    public boolean isUpsideDown() {
        return this.entityData.get(UPSIDE_DOWN);
    }

    public void setUpsideDown(boolean upsideDown) {
        this.entityData.set(UPSIDE_DOWN, upsideDown);
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(0, new TemptGoal(this, 1.2f, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(1, new FindWater(this));
        this.goalSelector.addGoal(1, new LeaveWater(this));
        this.goalSelector.addGoal(3, new SeafloorWander(this, 1.0D, 10, 50));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    public void tick() {
        this.lastTickPosX = this.getX();
        this.lastTickPosY = this.getY();
        this.lastTickPosZ = this.getZ();
        super.tick();

        if (!isInWater() && getHealth() <= 2) {
            if (!isUpsideDown() && !hasFlipped) {
                this.setUpsideDown(true);
            }
        }

        if (isInWater()) {
            this.setUpsideDown(false);
        }

        if (this.isUpsideDown()) {
            this.flippedAnimationState.startIfStopped(this.tickCount);
            getUp = getUp - 1;
            if (getUp <= 0) {
                this.setUpsideDown(false);
                this.flippedAnimationState.stop();
                this.hasFlipped = true;
                this.getUp = 80;
            }
        }

        if (this.level().isClientSide) {
            setupAnimationStates();
        }
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

    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING && isMoving()) {
            f = Math.min(pPartialTick + 6F, 1F);
        } else {
            f = 0F;
        }
        this.walkAnimation.update(f, 0.2F);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        setUpsideDown(tag.getBoolean("isUpsideDown"));
        super.readAdditionalSaveData(tag);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("isUpsideDown", this.isUpsideDown());
        super.addAdditionalSaveData(tag);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    protected SoundEvent getFlopSound() {
        return null;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.3F;
    }

    protected void handleAirSupply(int air) {
    }

    public boolean shouldStopMoving() {
        return this.isUpsideDown();
    }

    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.HORSESHOE_CRAB_BUCKET.get());
    }
}
