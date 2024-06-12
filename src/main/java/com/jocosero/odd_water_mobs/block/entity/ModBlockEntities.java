package com.jocosero.odd_water_mobs.block.entity;


import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, OddWaterMobs.MOD_ID);

    public static final RegistryObject<BlockEntityType<TrapperBlockEntity>> TRAPPER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("trapper_block_entity", () ->
                    BlockEntityType.Builder.of(TrapperBlockEntity::new,
                            ModBlocks.TRAPPER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
