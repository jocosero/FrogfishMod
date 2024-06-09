package com.jocosero.odd_water_mobs.potion;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, OddWaterMobs.MOD_ID);

    public static final RegistryObject<Potion> ALLURING_POTION = POTIONS.register("alluring_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ALLURING.get(), 3600, 0)));

    public static final RegistryObject<Potion> LONG_ALLURING_POTION = POTIONS.register("long_alluring_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ALLURING.get(), 9600, 0)));

    public static final RegistryObject<Potion> HEALING_VIGOR_POTION = POTIONS.register("healing_vigor_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALING_VIGOR.get(), 3600, 0)));

    public static final RegistryObject<Potion> POISON_PROTECTION_POTION = POTIONS.register("poison_protection_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.POISON_PROTECTION.get(), 4800, 0)));

    public static final RegistryObject<Potion> LONG_POISON_PROTECTION_POTION = POTIONS.register("long_poison_protection_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.POISON_PROTECTION.get(), 14400, 0)));

    public static final RegistryObject<Potion> MUDSKIPPING_POTION = POTIONS.register("mudskipping_potion",
            () -> new Potion(
                    new MobEffectInstance(MobEffects.JUMP, 2400, 2),
                    new MobEffectInstance(MobEffects.DIG_SPEED, 2400, 1),
                    new MobEffectInstance(MobEffects.WEAKNESS, 2400, 1)
            ));

    public static final RegistryObject<Potion> LONG_MUDSKIPPING_POTION = POTIONS.register("long_mudskipping_potion",
            () -> new Potion(
                    new MobEffectInstance(MobEffects.JUMP, 6000, 2),
                    new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1),
                    new MobEffectInstance(MobEffects.WEAKNESS, 6000, 1)
            ));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
