package com.jocosero.odd_water_mobs.entity.custom;

import com.jocosero.odd_water_mobs.entity.AbstractWaterMob;
import com.jocosero.odd_water_mobs.entity.ai.FindAndEatChumGoal;
import com.jocosero.odd_water_mobs.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class AnglerfishEntity extends AbstractWaterMob {

    private static final EntityDataAccessor<Boolean> PUFFED = SynchedEntityData.defineId(AnglerfishEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(AnglerfishEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
//    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState puffedAnimationState = new AnimationState();

    private double lastTickPosX, lastTickPosY, lastTickPosZ;
    private int idleAnimationTimeout = 0;

    public AnglerfishEntity(EntityType<? extends AbstractWaterMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AnglerfishEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FindAndEatChumGoal(this, 1.0));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.0D, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.2D, 40));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 2.0F));
    }

    @Override
    public void aiStep() {
        this.lastTickPosX = this.getX();
        this.lastTickPosY = this.getY();
        this.lastTickPosZ = this.getZ();
        super.aiStep();
        if (!isUnderWater()) {
            if (!isPuffed()) {
                this.puffedAnimationState.startIfStopped(this.tickCount);
                this.setPuffed(true);
            }
        }
        if (isUnderWater()) {
            this.puffedAnimationState.stop();
            this.setPuffed(false);
        }
        if (this.level().isClientSide) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0 && !this.isMoving()) {
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
        return dx * dx + dy * dy + dz * dz > 0.0003D;
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float speed;
        if (isMoving()) {
            speed = Math.min(partialTick + 6F, 1F);
        } else {
            speed = 0F;
        }
        this.walkAnimation.update(speed, 0.2F);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setPuffed(tag.getBoolean("isPuffed"));
        if (tag.contains("BucketVariantTag")) {
            this.setVariant(tag.getInt("BucketVariantTag"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isPuffed", this.isPuffed());
        tag.putInt("BucketVariantTag", this.getTypeVariant());
        ;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PUFFED, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(int variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant);
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), random.nextInt(2) == 0 ? 1F : 0F);
            }
        } else {
            this.setAirSupply(100);
        }
    }


    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.45F;
    }

    public boolean isPuffed() {
        return this.entityData.get(PUFFED);
    }

    public void setPuffed(boolean puffed) {
        this.entityData.set(PUFFED, puffed);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.ANGLERFISH_BUCKET.get());
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
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
}
