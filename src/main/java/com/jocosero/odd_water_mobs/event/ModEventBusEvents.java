package com.jocosero.odd_water_mobs.event;

import com.jocosero.odd_water_mobs.OddWaterMobs;
import com.jocosero.odd_water_mobs.entity.ModEntities;
import com.jocosero.odd_water_mobs.entity.custom.AnglerfishEntity;
import com.jocosero.odd_water_mobs.entity.custom.FrogfishEntity;
import com.jocosero.odd_water_mobs.entity.custom.HorseshoeCrabEntity;
import com.jocosero.odd_water_mobs.entity.custom.IsopodEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OddWaterMobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ANGLERFISH.get(), AnglerfishEntity.createAttributes().build());
        event.put(ModEntities.ISOPOD.get(), IsopodEntity.createAttributes().build());
        event.put(ModEntities.HORSESHOE_CRAB.get(), HorseshoeCrabEntity.createAttributes().build());
//        event.put(ModEntities.COELACANTH.get(), CoelacanthEntity.createAttributes().build());
        event.put(ModEntities.FROGFISH.get(), FrogfishEntity.createAttributes().build());
    }

}


