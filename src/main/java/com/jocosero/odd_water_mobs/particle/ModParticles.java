package com.jocosero.odd_water_mobs.particle;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OddWaterMobs.MOD_ID);

    public static final RegistryObject<SimpleParticleType> HYDROTHERMAL_SMOKE = PARTICLE_TYPES.register("hydrothermal_smoke", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> CHUM_CHUNKS = PARTICLE_TYPES.register("chum_chunks", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
