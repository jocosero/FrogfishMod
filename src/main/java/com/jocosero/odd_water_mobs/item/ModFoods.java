package com.jocosero.odd_water_mobs.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties FROGFISH = new FoodProperties.Builder()
            .nutrition(1)
            .saturationMod(0.1F)
            .build();

    public static final FoodProperties RAW_ANGLERFISH = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 0.3F)
            .meat()
            .build();

    public static final FoodProperties COOKED_ISOPOD = (new FoodProperties.Builder())
            .nutrition(6)
            .saturationMod(0.6F)
            .meat().build();

    public static final FoodProperties COELACANTH = (new FoodProperties.Builder())
            .nutrition(6)
            .saturationMod(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 0.6F)
            .meat()
            .build();

    public static final FoodProperties RAW_SPIDER_CRAB_LEG = (new FoodProperties.Builder())
            .nutrition(1)
            .saturationMod(0.3F)
            .meat()
            .build();

    public static final FoodProperties COOKED_SPIDER_CRAB_LEG = (new FoodProperties.Builder())
            .nutrition(7)
            .saturationMod(1.0F)
            .meat()
            .build();

    public static final FoodProperties RAW_GHOST_SHARK = (new FoodProperties.Builder())
            .nutrition(1)
            .saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 400, 0), 1.0F)
            .meat()
            .build();

    public static final FoodProperties RAW_MUDSKIPPER = (new FoodProperties.Builder())
            .nutrition(3)
            .saturationMod(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 0.6F)
            .meat()
            .build();

    public static final FoodProperties COOKED_MUDSKIPPER = (new FoodProperties.Builder())
            .nutrition(5)
            .saturationMod(0.5F)
            .meat()
            .build();

    public static final FoodProperties DEEP_SEA_FISH = (new FoodProperties.Builder())
            .nutrition(3)
            .saturationMod(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 0.6F)
            .meat()
            .build();

    public static final FoodProperties RAW_SQUAT_LOBSTER_TAIL = (new FoodProperties.Builder())
            .nutrition(1)
            .saturationMod(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 0.6F)
            .meat()
            .build();

    public static final FoodProperties COOKED_SQUAT_LOBSTER_TAIL = (new FoodProperties.Builder())
            .nutrition(5)
            .saturationMod(0.8F)
            .meat()
            .build();

    public static final FoodProperties SEA_PIG = (new FoodProperties.Builder())
            .nutrition(3)
            .saturationMod(0F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 1200, 0), 0.8F)
            .meat()
            .build();
}
