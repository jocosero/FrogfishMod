package com.jocosero.odd_water_mobs.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

public class HealingVigorEffect extends MobEffect {
    private boolean effectApplied = false;

    public HealingVigorEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.getCommandSenderWorld().isClientSide && entity instanceof Player player) {
            ItemStack itemStack = player.getItemInHand(entity.getUsedItemHand());
            int stop = player.getUseItemRemainingTicks();
            if (itemStack.isEdible() && (stop <= 0) && !effectApplied) {
                FoodProperties foodProperties = itemStack.getItem().getFoodProperties();
                if (foodProperties != null) {
                    int food = foodProperties.getNutrition();
                    float saturation = foodProperties.getSaturationModifier();
                    player.heal(food * 0.25F);
                    player.getFoodData().setSaturation(
                            (float) (player.getFoodData().getSaturationLevel() + Math.ceil(saturation / 2)));
                    effectApplied = true;
                }
            } else if (stop == itemStack.getUseDuration()) {
                effectApplied = false;
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
