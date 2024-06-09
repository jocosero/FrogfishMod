package com.jocosero.odd_water_mobs.effect;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, OddWaterMobs.MOD_ID);

    public static final RegistryObject<MobEffect> ALLURING = MOB_EFFECTS.register("alluring",
            () -> new AlluringEffect(MobEffectCategory.NEUTRAL, 12247757));

    public static final RegistryObject<MobEffect> SINKING = MOB_EFFECTS.register("sinking",
            () -> new AlluringEffect(MobEffectCategory.NEUTRAL, 41021112));

    public static final RegistryObject<MobEffect> POISON_PROTECTION = MOB_EFFECTS.register("poison_protection",
            () -> new PoisonProtectionEffect(MobEffectCategory.NEUTRAL, 16104125));

    public static final RegistryObject<MobEffect> HEALING_VIGOR = MOB_EFFECTS.register("healing_vigor",
            () -> new HealingVigorEffect(MobEffectCategory.BENEFICIAL, -16750849));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
