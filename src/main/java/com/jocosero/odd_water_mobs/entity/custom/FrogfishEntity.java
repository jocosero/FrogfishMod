package com.jocosero.odd_water_mobs.entity.custom;

import com.jocosero.odd_water_mobs.entity.AbstractWaterMob;
import com.jocosero.odd_water_mobs.entity.ai.SwimToSeafloor;
import com.jocosero.odd_water_mobs.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FrogfishEntity extends AbstractWaterMob {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(FrogfishEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();

    private double lastTickPosX, lastTickPosY, lastTickPosZ;
    private int idleAnimationTimeout = 0;

    public FrogfishEntity(EntityType<? extends AbstractWaterMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if (!this.fromBucket()) {
            this.setVariant(this.random.nextInt(3));
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FrogfishEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.0D, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(3, new SwimToSeafloor(this, 1.0D, 300));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 2.0F));
    }

    public void aiStep() {
        this.lastTickPosX = this.getX();
        this.lastTickPosY = this.getY();
        this.lastTickPosZ = this.getZ();
        if (this.isUnderWater() && isMoving()) {
            this.swimmingAnimationState.startIfStopped(this.tickCount);
        }
        if (this.level().isClientSide) {
            setupAnimationStates();
        }
        super.aiStep();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pAdditionalData) {
        if (this.fromBucket() && pAdditionalData != null && pAdditionalData.contains("BucketVariantTag")) {
            this.setVariant(pAdditionalData.getInt("BucketVariantTag"));
        } else {
            this.setVariant(this.random.nextInt(3));
        }
        return pSpawnData;
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
        if (isMoving()) {
            speed = Math.min(partialTick + 6F, 1F);
        } else {
            speed = 0F;
        }
        this.walkAnimation.update(speed, 0.2F);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater() && this.onGround()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (this.jumping) {
                this.setDeltaMovement(this.getDeltaMovement().scale(1.4D));
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.9D, 0.0D));
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.4D));
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.01D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("BucketVariantTag")) {
            this.setVariant(tag.getInt("BucketVariantTag"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("BucketVariantTag", this.getTypeVariant());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(int variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant);
    }

    @Override
    public void saveToBucketTag(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (!nbt.contains("BucketVariantTag")) {
            super.saveToBucketTag(itemStack);
            nbt.putInt("BucketVariantTag", this.getTypeVariant());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound) {
        super.loadFromBucketTag(compound);
        if (compound.contains("BucketVariantTag")) {
            this.setVariant(compound.getInt("BucketVariantTag"));
        }
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.FROGFISH_BUCKET.get());
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COD_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }
}
