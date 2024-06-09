package com.jocosero.odd_water_mobs.entity;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.entity.custom.FrogfishEntity;
import com.jocosero.odd_water_mobs.entity.custom.HorseshoeCrabEntity;
import com.jocosero.odd_water_mobs.entity.custom.IsopodEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OddWaterMobs.MOD_ID);

    public static final RegistryObject<EntityType<FrogfishEntity>> FROGFISH =
            ENTITY_TYPES.register("frogfish", () -> EntityType.Builder.of(FrogfishEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.35f, 0.3f).build("frogfish"));

    public static final RegistryObject<EntityType<IsopodEntity>> ISOPOD =
            ENTITY_TYPES.register("isopod", () -> EntityType.Builder.of(IsopodEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.5f, 0.35f).build("isopod"));

    public static final RegistryObject<EntityType<HorseshoeCrabEntity>>  HORSESHOE_CRAB =
            ENTITY_TYPES.register("horseshoe_crab", () -> EntityType.Builder.of(HorseshoeCrabEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.65f, 0.3f).build("horseshoe_crab"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
