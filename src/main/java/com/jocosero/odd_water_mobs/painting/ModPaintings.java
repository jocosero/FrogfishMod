package com.jocosero.odd_water_mobs.painting;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANT =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, OddWaterMobs.MOD_ID);

    public static final RegistryObject<PaintingVariant> COLOSSAL = PAINTING_VARIANT.register("colossal",
            () -> new PaintingVariant(64, 32));

    public static void register(IEventBus eventBus) {
        PAINTING_VARIANT.register(eventBus);
    }
}
